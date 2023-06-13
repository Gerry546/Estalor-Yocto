FILESEXTRAPATHS:prepend:reterminal := "${THISDIR}/files:"
SRC_URI:append:reterminal = " file://raspberrypi-rauc.rules"

do_install:append:reterminal() {
    install -m 0644 ${WORKDIR}/raspberrypi-rauc.rules ${D}${sysconfdir}/udev/mount.blacklist.d/
}
