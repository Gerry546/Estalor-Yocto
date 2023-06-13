DESCRIPTION = "Boot images"
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
    rpi-bootfiles:do_deploy \
"

do_deploy () {
    FATSOURCEDIR="${WORKDIR}/reterminal-boot"
    mkdir -p ${FATSOURCEDIR}

    mkdir -p ${FATSOURCEDIR}/VFAT/BOOT
    cp ${DEPLOY_DIR_IMAGE}/bootfiles/* ${FATSOURCEDIR}/VFAT/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/boot.scr    ${FATSOURCEDIR}/VFAT/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/Image       ${FATSOURCEDIR}/VFAT/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/bcm2711-rpi-400.dtb    ${FATSOURCEDIR}/VFAT/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/bcm2711-rpi-4-b.dtb    ${FATSOURCEDIR}/VFAT/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/bcm2711-rpi-cm4.dtb    ${FATSOURCEDIR}/VFAT/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/bcm2711-rpi-cm4s.dtb   ${FATSOURCEDIR}/VFAT/BOOT/

    MKDOSFS_EXTRAOPTS="-S 512"
    FATIMG="${WORKDIR}/reterminal-boot.vfat"
    BLOCKS=65572

    rm -f ${FATIMG}

    mkdosfs -n "BOOT" ${MKDOSFS_EXTRAOPTS} -C ${FATIMG} \
                    ${BLOCKS}
    # Copy FATSOURCEDIR recursively into the image file directly
    mcopy -i ${FATIMG} -s ${FATSOURCEDIR}/* ::/
    chmod 644 ${FATIMG}

    mv ${FATIMG} ${DEPLOYDIR}/
}

do_deploy[cleandirs] += "${WORKDIR}/reterminal-boot"

addtask deploy after do_install