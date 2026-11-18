param(
  [string]$Out = "work\build\offline.apk"
)
$ErrorActionPreference = 'Stop'
$env:PYTHONIOENCODING = 'utf-8'
$cp = (Get-ChildItem work\tools\libs\*.jar | ForEach-Object { $_.FullName }) -join ';'
$bt = 'work\tools\bt\android-14'

Write-Host '[1/5] assembling smali'
java -cp $cp org.jf.smali.Main a work\build\smali -o work\build\classes_patched.dex
if ($LASTEXITCODE -ne 0) { throw 'smali failed' }

Write-Host '[2/5] packing apk'
$dex2 = $null
if (Test-Path work\java\dex\classes.dex) { $dex2 = 'work\java\dex\classes.dex' }
python work\rebuild_apk.py base.apk work\build\classes_patched.dex work\build\patched_unaligned.apk $dex2
if ($LASTEXITCODE -ne 0) { throw 'pack failed' }

Write-Host '[3/5] aligning'
& "$bt\zipalign.exe" -f 4 work\build\patched_unaligned.apk work\build\patched.apk
if ($LASTEXITCODE -ne 0) { throw 'zipalign failed' }

Write-Host '[4/5] signing'
if (-not (Test-Path work\build\debug.keystore)) {
  & keytool -genkeypair -keystore work\build\debug.keystore -alias debug -keyalg RSA -keysize 2048 -validity 10000 -storepass android -keypass android -dname "CN=Local Debug" | Out-Null
}
& "$bt\apksigner.bat" sign --ks work\build\debug.keystore --ks-pass pass:android --key-pass pass:android --v1-signing-enabled true --v2-signing-enabled true --out $Out work\build\patched.apk
if ($LASTEXITCODE -ne 0) { throw 'sign failed' }

Write-Host "[5/5] done: $Out $((Get-Item $Out).Length) bytes"
