$ErrorActionPreference = "Stop"

Set-Location $PSScriptRoot
Set-Location "backend"

if (-not (Test-Path ".\target\lms-backend-1.0.0.jar")) {
  & "..\tools\apache-maven-3.9.9\bin\mvn.cmd" -q -DskipTests package
}

& "java" "-jar" ".\target\lms-backend-1.0.0.jar"
