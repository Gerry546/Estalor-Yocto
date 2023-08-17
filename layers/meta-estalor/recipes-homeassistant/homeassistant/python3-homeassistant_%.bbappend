FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://HA-setup \
    file://homeassistant-setup.service \
"

HOMEASSISTANT_CONFIG_DIR = "/ha-conf"
HOMEASSISTANT_USER = "root"

FILES:${PN} += "\
    ${HOMEASSISTANT_CONFIG_DIR} \
"

SYSTEMD_SERVICE:${PN}:append = "\
    homeassistant-setup.service\
"

do_install:append () {
    install -m 0755 ${WORKDIR}/HA-setup ${D}${bindir}
    install -m 0644 ${WORKDIR}/homeassistant-setup.service ${D}${systemd_unitdir}/system
    sed -i -e 's,@HOMEASSISTANT_USER@,${HOMEASSISTANT_USER},g' ${D}${systemd_unitdir}/system/homeassistant-setup.service
}