SUMMARY = "Kernel module code for the reterminal screen"
DESCRIPTION = "${SUMMARY}"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRC_URI = "\
    file://Makefile \
    file://ltr30x.c \
    file://dkms.conf \
    file://COPYING \
"

S = "${WORKDIR}"

inherit module

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES:${PN} += "kernel-module-reterminal-screen"
