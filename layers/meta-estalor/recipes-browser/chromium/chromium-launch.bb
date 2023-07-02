LICENSE = "CLOSED"

SRC_URI = " \
    file://chromium.service.in \
"

S = "${WORKDIR}"

inherit allarch systemd

RDEPENDS:${PN} += "weston-init"

URL ?= "http://localhost:8123"

do_compile() {
    sed -e "s:@@URL@@:${URL}:" \
    chromium.service.in > chromium.service
}

do_install() {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/chromium.service ${D}${systemd_unitdir}/system
}

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "chromium.service"