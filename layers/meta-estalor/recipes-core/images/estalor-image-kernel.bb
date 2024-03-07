# Original item copied from:
# Copyright (C) 2021 Enrico Jorns <ejo@pengutronix.de>
# Released under the MIT license (see COPYING.MIT for the terms)
SUMMARY = "Kernel boot image"
DESCRIPTION = "\
    Image recipe which is used to populate a partition containing the boot scripts for uboot \
    and the kernel image. Mainly aimed to circumvent the challenge of working with RPI products \
    where the boot partition itself is adjusted in the boot scheme"
LICENSE = "MIT"

inherit nopackages deploy

do_fetch[noexec] = "1"
do_patch[noexec] = "1"
do_compile[noexec] = "1"
do_install[noexec] = "1"
deltask do_populate_sysroot

do_deploy[depends] += "\
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    virtual/kernel:do_deploy \
    virtual/bootloader:do_deploy \
"

PARTITION_NAME = "kernel-part"

do_deploy () {
    FATSOURCEDIR="${WORKDIR}/${PARTITION_NAME}/"
    mkdir -p ${FATSOURCEDIR}

    mkdir -p ${FATSOURCEDIR}/part/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/boot.scr ${FATSOURCEDIR}/
    cp ${DEPLOY_DIR_IMAGE}/Image ${FATSOURCEDIR}/

    MKDOSFS_EXTRAOPTS="-S 512"
    FATIMG="${WORKDIR}/${PARTITION_NAME}.vfat"
    BLOCKS=65572

    rm -f ${FATIMG}

    mkdosfs -n "BOOT" ${MKDOSFS_EXTRAOPTS} -C ${FATIMG} \
                    ${BLOCKS}
    # Copy FATSOURCEDIR recursively into the image file directly
    mcopy -i ${FATIMG} -s ${FATSOURCEDIR}/* ::/
    chmod 644 ${FATIMG}

    mv ${FATIMG} ${DEPLOYDIR}/
}

do_deploy[cleandirs] += "${WORKDIR}/${PARTITION_NAME}"

addtask deploy after do_install
