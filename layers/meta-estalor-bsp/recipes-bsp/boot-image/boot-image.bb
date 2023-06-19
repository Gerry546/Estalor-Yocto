# Copyright (C) 2021 Enrico Jorns <ejo@pengutronix.de>
# Released under the MIT license (see COPYING.MIT for the terms)

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
    rpi-config:do_deploy \
    rpi-bootfiles:do_deploy \
    u-boot:do_deploy \
    u-boot-default-script:do_deploy \
"

do_deploy () {
    FATSOURCEDIR="${WORKDIR}/raspberrypi-boot/"
    mkdir -p ${FATSOURCEDIR}

    mkdir -p ${FATSOURCEDIR}/rpi/BOOT/overlays
    cp ${DEPLOY_DIR_IMAGE}/bootfiles/* ${FATSOURCEDIR}/rpi/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/bcm2711-rpi-400.dtb ${FATSOURCEDIR}/rpi/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/bcm2711-rpi-4-b.dtb ${FATSOURCEDIR}/rpi/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/bcm2711-rpi-cm4.dtb ${FATSOURCEDIR}/rpi/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/bcm2711-rpi-cm4s.dtb ${FATSOURCEDIR}/rpi/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/boot.scr ${FATSOURCEDIR}/rpi/BOOT/
    cp ${DEPLOY_DIR_IMAGE}/*.dtbo ${FATSOURCEDIR}/rpi/BOOT/overlays
    cp ${DEPLOY_DIR_IMAGE}/Image ${FATSOURCEDIR}/rpi/BOOT
    cp ${DEPLOY_DIR_IMAGE}/u-boot.bin ${FATSOURCEDIR}/rpi/BOOT/${SDIMG_KERNELIMAGE}

    MKDOSFS_EXTRAOPTS="-S 512"
    FATIMG="${WORKDIR}/raspberrypi-boot.vfat"
    BLOCKS=65572

    rm -f ${FATIMG}

    mkdosfs -n "BOOT" ${MKDOSFS_EXTRAOPTS} -C ${FATIMG} \
                    ${BLOCKS}
    # Copy FATSOURCEDIR recursively into the image file directly
    mcopy -i ${FATIMG} -s ${FATSOURCEDIR}/* ::/
    chmod 644 ${FATIMG}

    mv ${FATIMG} ${DEPLOYDIR}/
}

do_deploy[cleandirs] += "${WORKDIR}/raspberrypi-boot"

addtask deploy after do_install