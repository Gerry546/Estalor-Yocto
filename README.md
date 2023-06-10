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

## Dockerfile
A dockerfile has been created to allow for consistently having the same build environment.
A docker image can be created using:
```
$ cd utils
$ sudo docker build --no-cache --tag "estalor-yocto:latest" .
```
Note that when using a docker container with mounted volumes that PUID and GUID on the host and container should be identical. So you could also build it using:
```
$ sudo docker build --no-cache --build-arg "host_uid=$(id -u)" \
  --build-arg "host_gid=$(id -g)" --tag "estalor-yocto:latest" .
```

After the build is constructed you can run the docker image from the root directory using: 
```
$ ssh yocto@localhost
```
The password is `yocto`.

# Running the build
To build everything we use, as described `kas`.
To kick off a build you can run:
```
$ python3 -m kas build kas/<image>.yml
```
There are several images available and they will be describe in the following sections.
Furthermore there are the layers in the sources folder so if you can always do the normal way of using yoct by:
```
$ source sources/poky/oe-init-build-env
```

## Image: Estalor-qemuarm53
One of the regular targets for the Estalor projects is a variety of Raspberry Pi 3 or 4 based products. To facilitate testing with this easily
a qemu based image is created in order to do easy development.

To run the image you can do the following:
```
$ runqemu tmp/deploy/images/qemuarm64-a53/estalor-debug-image-qemuarm64-a53.qemuboot.conf slirp wic nographic
```