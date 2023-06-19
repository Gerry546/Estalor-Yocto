FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:qemuarm64-a53 = " file://virtio.cfg"