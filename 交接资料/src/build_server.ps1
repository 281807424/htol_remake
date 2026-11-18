param()
$ErrorActionPreference = 'Stop'
$srcDir = 'work\java\htol\gamesuper'
$classes = 'work\java\classes'
$dexDir = 'work\java\dex'
$d8 = 'work\tools\bt\android-14\d8.bat'

if (Test-Path $classes) { Remove-Item -Recurse -Force $classes }
New-Item -ItemType Directory -Force $classes | Out-Null
$sources = Get-ChildItem $srcDir -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 --release 8 -d $classes $sources
if ($LASTEXITCODE -ne 0) { throw 'javac failed' }

if (Test-Path "$dexDir\classes.dex") { Remove-Item -Force "$dexDir\classes.dex" }
$classFiles = Get-ChildItem $classes -Recurse -Filter *.class | ForEach-Object { $_.FullName }
& $d8 --min-api 21 --output $dexDir @classFiles
if ($LASTEXITCODE -ne 0) { throw 'd8 failed' }
Write-Host "server dex: $((Get-Item "$dexDir\classes.dex").Length) bytes"
