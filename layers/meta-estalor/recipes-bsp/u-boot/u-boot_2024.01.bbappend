FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

DEPENDS:append:qemuarm64 = " \
    bison-native \
    u-boot-mkimage-native \
"

SRC_URI:append:qemuarm64 = " \
    file://boot-qemu.cmd.in \
    file://fw_env.config \
"

SRC_URI:append:reterminal = " \
    file://fw_env.config \
"

# file://0001-Clean-boot-options.patch
# file://0001-place-uboot_env-in-boot-parts.patch

do_configure:append:qemuarm64() {
    sed -e 's/@@KERNEL_IMAGETYPE@@/${KERNEL_IMAGETYPE}/' \
    -e 's/@@KERNEL_BOOTCMD@@/${KERNEL_BOOTCMD}/' \
    "${WORKDIR}/boot-qemu.cmd.in" > "${WORKDIR}/boot.cmd"
    mkimage -C none -A ${UBOOT_ARCH} -T script -d "${WORKDIR}/boot.cmd" boot.scr
}

do_install:append:qemuarm64() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}

do_deploy:append:qemuarm64() {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/boot.scr ${DEPLOYDIR}
}

addtask do_deploy after do_compile before do_build
