FILESEXTRAPATHS:prepend := "${THISDIR}/linux-yocto:"

SRC_URI:append:qemuarm64-a53 = " file://virtio.cfg"