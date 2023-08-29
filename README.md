# Estalor-Yocto
A single project for all Yocto based home automation

## Goal of the project
The goal of the project is to provide custom Linux based solutions for various devices in my home in order to be part of my home automation setup.

# Getting started
## Yocto Project
To get started first we will need some build tools.
Ensure you have everything installed per the requirements for the [Yocto/OpenEmbedded project](https://docs.yoctoproject.org/).

## Kas
Next up, we will use [Kas](https://kas.readthedocs.io/en/3.2.3/) as a setup tool. Kas is a Python tool so in order to not pollute the host you are working on we will configure a virtual environment.

    $ python3 -m venv .venv
    $ source .venv/bin/activate
    $ pip3 install kas

## Shared state directories
This repository assumes you will be using a shared sstate and downloads folder outside of your regular build directory. These are specified in the file [common-kas.yml](kas/include/common-kas.yml). You can either create these directories on your system or overwrite these paths in your own build.

## Dockerfile
A dockerfile has been created to allow for consistently having the same build environment.
A docker image can be created using:

    $ cd utils
    $ sudo docker build --no-cache --tag "estalor-yocto:latest" .

Note that when using a docker container with mounted volumes that PUID and GUID on the host and container should be identical. So you could also build it using:

    $ sudo docker build --no-cache --build-arg "host_uid=$(id -u)" --build-arg "host_gid=$(id -g)" --tag "estalor-yocto:latest" .


After the build is constructed you can run the docker image from the root directory using: 

    $ ssh yocto@localhost

The password is `yocto`.

# Compiling
To build everything we use, as described `kas`.
To kick off a build you can run:
```
$ python3 -m kas build kas/<image>.yml
```
There are several images available and they will be describe in the following sections.
Furthermore there are the layers in the sources folder so if you can always do the normal way of using yocto by:
```
$ source sources/poky/oe-init-build-env
```

# Image: estalor-qemuarm64-a72
One of the regular targets for the Estalor projects is a variety of Raspberry Pi 3 or 4 based products. To facilitate testing with this easily
a qemu based image is created in order to do easy development.

## Running
To run the image you can do the following:
```
$ runqemu tmp/deploy/images/qemuarm64-a72/estalor-image-debug-qemuarm64-a72.qemuboot.conf slirp wic nographic
```

## Updating

First check if RAUC is working correctly::

    # rauc status

Note:
By default using ``slirp`` will forward ports 22 and 23 on the qemu system to ports 2222 and 2323 on the host system.
If there is a collision with another runqemu instance, the script will pick the next free port.
You can define custom port forwarding by setting ``hostfwd`` in ``QB_SLIRP_OPT``. Examples::

    $ QB_SLIRP_OPT="-netdev user,id=net0,hostfwd=tcp::<host system port>-:<qemu system port>" runqemu core-image-minimal wic nographic ovmf slirp

    $ QB_SLIRP_OPT="-netdev user,id=net0,hostfwd=tcp::2222-:22,hostfwd=tcp::2323-:23" runqemu core-image-minimal wic nographic ovmf slirp

Slirp can be useful for remote access to the virtual machine without needing root access to the host machine.
Keep in mind firewalls on both the host and the qemu machines should be configured based on your needs.

Obtain an IP address on the target::

    # udhcpc -i eth0

Copy update Bundle from host to the target::

    $  scp -P 2222 -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null build/tmp/deploy/images/qemuarm64-a72/estalor-qemuarm64-bundle-debug-qemuarm64-a72.raucb  root@localhost:/tmp

Check Bundle on the target::

    # rauc info /tmp/estalor-qemuarm64-debug-bundle-qemuarm64-a72.raucb

Install the Bundle::

    # rauc install /tmp/estalor-qemuarm64-debug-bundle-qemuarm64-a72.raucb
    installing
      0% Installing
      0% Determining slot states
     20% Determining slot states done.
     20% Checking bundle
     20% Verifying signature
     40% Verifying signature done.
     40% Checking bundle done.
     40% Checking manifest contents
     60% Checking manifest contents done.
     60% Determining target install group
     80% Determining target install group done.
     80% Updating slots
     80% Checking slot efi.0
     85% Checking slot efi.0 done.
     85% Copying image to efi.0
     90% Copying image to efi.0 done.
     90% Checking slot rootfs.1
     95% Checking slot rootfs.1 done.
     95% Copying image to rootfs.1
     100% Copying image to rootfs.1 done.
     100% Updating slots done.
     100% Installing done.
     Installing `/tmp/qemu-demo-bundle-qemux86-64.raucb` succeeded

Reboot the system::

    # systemctl reboot

# Image: estalor-aarch64-reterminal
In my home automation system I have a small reterminal module from [Seeed Studios](https://www.seeedstudio.com/) to play with.
Goal is to run native home assistant on it and connect it to the rest of the ecosystem.

## Features:
- RAUC A/B partitioning
- HomeAssistant build from sources

## Additional required tools
For running on the target we need to have programs capable of flashing the MMC memory of the reterminal.
Install ``rpiboot`` by following the steps detailed [here](https://github.com/raspberrypi/usbboot#building)

## Updating partition scheme
When not building with TFTP, usually in your final image, or if you want to update the devicetree (since raspberry pi has a different boot sequence), 
you can flash it via this way:

- Unscrew the back as dictated in on the website [here](https://wiki.seeedstudio.com/reTerminal/#flash-raspberry-pi-os-64-bit-ubuntu-os-or-other-os-to-emmc).
- Connect a debug UART to the pins on the side of the reterminal (number 6, 8 and 10). Look [here](https://wiki.seeedstudio.com/reTerminal/#pinout-diagram) for the diagram.
- Start picocom::

      $ picocom -b 115200 /dev/ttyUSB0

- Start rpiboot: 

      $ sudo ~/tools/usbboot/rpiboot

- Connect a USB cable from your workstation to your reterminal. It should boot ``rpiboot`` should be able to finish.
- Check where the drives are in ``/dev`` and unsure they are unmounted.
- Copy the new image in .wic/bz2 format to the reterminal:

      $ sudo bmaptool copy estalor-image-reterminal.wic.bz2 /dev/sda

- If it is done, unplug the reterminal, flip the boot switch and boot again. It should now boot normally.