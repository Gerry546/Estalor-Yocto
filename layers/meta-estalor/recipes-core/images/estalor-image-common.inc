inherit core-image

IMAGE_INSTALL = "\
    packagegroup-core-boot \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    rauc \
    openssh \
    openssh-sshd \
    openssh-scp \
    udev-extraconf \
    git \
    \
    python3-homeassistant \
    \
    weston \
    weston-init \
"

IMAGE_INSTALL:append:reterminal = "\
    kernel-modules \
    kernel-module-ltr30x \
    kernel-module-lis3lv02d \
    kernel-module-panel-ili9881d \
    linux-firmware-rpidistro-bcm43455 \
    chromium-ozone-wayland \
    chromium-launch \
    button-handler \
"

IMAGE_FEATURES = "\
    weston \
"

IMAGE_FEATURES:append:reterminal = "\
"

IMAGE_LINGUAS = "\
"

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"


# Optimizations for RAUC adaptive method 'block-hash-index'
# rootfs image size must to be 4K-aligned
IMAGE_ROOTFS_ALIGNMENT = "4"
# ext4 block and inode size should be set to 4K
EXTRA_IMAGECMD:ext4 = "-i 4096 -b 4096"
