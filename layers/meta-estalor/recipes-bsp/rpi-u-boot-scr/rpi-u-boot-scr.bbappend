FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "\
    file://boot-default.cmd.in \
"

do_compile() {
    sed -e 's/@@KERNEL_IMAGETYPE@@/${KERNEL_IMAGETYPE}/' \
    -e 's/@@KERNEL_BOOTCMD@@/${KERNEL_BOOTCMD}/' \
    "${WORKDIR}/boot-default.cmd.in" > "${WORKDIR}/boot.cmd"

    mkimage -A ${UBOOT_ARCH} -T script -C none -n "Boot script" -d "${WORKDIR}/boot.cmd" boot.scr
}
