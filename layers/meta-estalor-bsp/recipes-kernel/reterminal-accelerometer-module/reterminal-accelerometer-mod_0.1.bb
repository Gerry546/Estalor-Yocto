SUMMARY = "Kernel module code for the reterminal accelerometer"
DESCRIPTION = "${SUMMARY}"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRC_URI = "\
    file://Makefile \
    file://lis3lv02d_i2c.c \
    file://lis3lv02d.c \
    file://lis3lv02d.h \
    file://COPYING \
"

S = "${WORKDIR}"

inherit module

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES:${PN} += "kernel-module-reterminal-accelerometer"
