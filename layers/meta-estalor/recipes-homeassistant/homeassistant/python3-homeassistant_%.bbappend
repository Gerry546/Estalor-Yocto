FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://HA-setup \
"

HOMEASSISTANT_CONFIG_DIR = "/ha-conf"
HOMEASSISTANT_USER = "root"

FILES:${PN} += "\
    ${HOMEASSISTANT_CONFIG_DIR} \
"

do_install:append () {
    install -m 0755 ${WORKDIR}/HA-setup ${D}${bindir}
}
