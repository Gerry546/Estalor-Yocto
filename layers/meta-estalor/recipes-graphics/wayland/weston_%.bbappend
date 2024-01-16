FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://ha-splash.png \
"

do_install:append() {
    install -D -p -m 0644 ${WORKDIR}/ha-splash.png ${D}${datadir}/backgrounds/ha-splash.png
}

FILES:${PN} += "\
    ${datadir}/backgrounds/ \
"
