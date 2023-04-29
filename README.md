# Estalor-Project
A single project for all Yocto based home automation

## Goal of the project
The goal of the project is to provide custom Linux based solutions for various devices in my home in order to be part of my home automation setup.

# Getting started
## Yocto Project
To get started first we will need some build tools.
Ensure you have everything installed per the requirements for the [Yocto/OpenEmbedded project](https://docs.yoctoproject.org/).

## Kas
Next up, we will use [Kas](https://kas.readthedocs.io/en/3.2.3/) as a setup tool. Kas is a Python tool so in order to not pollute the host you are working on we will configure a virtual environment.
```
$ python3 -m venv .venv
$ source .venv/bin/activate
$ pip3 install kas
```

## Shared state directories
This repository assumes you will be using a shared sstate and downloads folder outside of your regular build directory. These are specified in the file [common-kas.yml](kas/include/common-kas.yml). You can either create these directories on your system or overwrite these paths in your own build.
