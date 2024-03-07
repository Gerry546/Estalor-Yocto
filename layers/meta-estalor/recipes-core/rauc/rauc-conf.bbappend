FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

FILES:${PN} += "\
    /data/rauc \
"

# Additional dependencies required to run RAUC on the target
RDEPENDS:${PN} += "\
    libubootenv \
    u-boot-env \
    u-boot-fw-utils \
"

do_install:append:reterminal() {
    install -d ${D}/data/rauc
}
