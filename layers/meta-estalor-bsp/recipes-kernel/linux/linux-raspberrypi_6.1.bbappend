FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:reterminal = "\
    file://reterminal.dts;subdir=git/arch/arm/boot/dts/overlays/ \
    file://0001-Updated-Makefile-for-reterminal.dts.patch \
    file://rauc.cfg \
"
