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
    python3-homeassistant-axis \
    python3-homeassistant-backup \
    python3-homeassistant-cast \
    python3-homeassistant-fritz \
    python3-homeassistant-fritzbox \
    python3-homeassistant-hacs \
    python3-homeassistant-ipp \
    python3-homeassistant-met \
    python3-homeassistant-octoprint \
    python3-homeassistant-radio-browser \
    python3-homeassistant-shelly \
    python3-homeassistant-tts \
    python3-homeassistant-upnp \ 
"

IMAGE_INSTALL:append:reterminal = "\
    kernel-modules \
    kernel-module-ltr30x \
    kernel-module-lis3lv02d \
    kernel-module-panel-ili9881d \
    linux-firmware-rpidistro-bcm43455 \
    weston \
    weston-init \
    chromium-ozone-wayland \
    chromium-launch \
    button-handler \
"

IMAGE_FEATURES = "\
"

IMAGE_FEATURES:append:reterminal = "\
    weston \
"

IMAGE_LINGUAS = "\
"

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"

IMAGE_NAME_SUFFIX = ""

# Optimizations for RAUC adaptive method 'block-hash-index'
# rootfs image size must to be 4K-aligned
IMAGE_ROOTFS_ALIGNMENT = "4"
# ext4 block and inode size should be set to 4K
EXTRA_IMAGECMD:ext4 = "-i 4096 -b 4096"
