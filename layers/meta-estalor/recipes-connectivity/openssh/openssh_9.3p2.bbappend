FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://id_ed25519 \
    file://id_ed25519.pub \
    file://config \
"

do_install:append() {
    install -d ${D}/home/root/.ssh
    install -m 600 ${WORKDIR}/id_ed25519 ${D}/home/root/.ssh/
    install -m 600 ${WORKDIR}/id_ed25519.pub ${D}/home/root/.ssh/
    install -m 600 ${WORKDIR}/config ${D}/home/root/.ssh/
}

FILES:${PN} += "\
    /home/root/.ssh/id_ed25519 \
    /home/root/.ssh/id_ed25519.pub \
    /home/root/.ssh/config \
"