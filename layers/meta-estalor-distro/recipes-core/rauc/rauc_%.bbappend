FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Additional dependencies required to run RAUC on the target
RDEPENDS:${PN} += "\
    libubootenv \
    u-boot-env \
    u-boot-fw-utils \
"

do_install:append:reterminal() {
    install -d ${D}/data/rauc
}

FILES:${PN} += "\
    /data/rauc \
"