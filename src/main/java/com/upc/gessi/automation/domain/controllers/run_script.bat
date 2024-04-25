#!/bin/bash

# Obtener la ruta del directorio del script Python
#SCRIPT_DIR="$( cd "$( dirname "$0" )" && pwd )"

# Cambiar al directorio del script Python
#cd "$SCRIPT_DIR/../../../../../../../../../LD-queryGenerator"

# Ejecutar el script Python desde su propio directorio
#python3 QueryGenerator.py

@echo off
REM Obtener la ruta del directorio del script Python
set "SCRIPT_DIR=%~dp0"

REM Cambiar al directorio del script Python
cd /d "%SCRIPT_DIR%\..\..\..\..\..\..\..\LD-queryGenerator"

REM Ejecutar el script Python desde su propio directorio
python QueryGenerator.py
