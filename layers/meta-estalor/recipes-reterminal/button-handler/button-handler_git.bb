SUMMARY = "Application for handling button command on the reterminal"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

DEPENDS = "libevdev"

PV = "1.0+git${SRCPV}"

SRC_URI = "\
    git://github.com/Estalor/reterminal-button-handler.git;protocol=https;branch=main \
    file://button-handler.service \
"

SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit meson systemd pkgconfig

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/build/button-handler ${D}${bindir}/button-handler

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/button-handler.service ${D}${systemd_unitdir}/system
}

FILES:${PN} += "\
    ${bindir}/button-handler \
"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "button-handler.service"
