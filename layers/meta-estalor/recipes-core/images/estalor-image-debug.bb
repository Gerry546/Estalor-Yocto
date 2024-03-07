SUMMARY = "The Image to run the estalor image with debugging tools"
LICENSE = "CLOSED"

require estalor-image-common.inc

IMAGE_FEATURES:append = "\
    debug-tweaks \
"

IMAGE_INSTALL:append = " \
    nano \
"

IMAGE_INSTALL:append:reterminal = " \
    evtest \
    i2c-tools \
    weston-examples \
    strace \
    wayland-utils \
"
