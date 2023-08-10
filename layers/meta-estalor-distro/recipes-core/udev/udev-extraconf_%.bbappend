FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append:reterminal = "file://raspberrypi-rauc.rules"
SRC_URI:append:qemuarm64 = "file://qemuarm64-rauc.rules"

do_install:append:reterminal() {
    install -m 0644 ${WORKDIR}/raspberrypi-rauc.rules ${D}${sysconfdir}/udev/mount.ignorelist.d/
}

do_install:append:qemuarm64() {
    install -m 0644 ${WORKDIR}/qemuarm64-rauc.rules ${D}${sysconfdir}/udev/mount.ignorelist.d/
}
