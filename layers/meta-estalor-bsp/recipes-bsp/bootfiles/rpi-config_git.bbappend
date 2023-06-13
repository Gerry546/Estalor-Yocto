do_deploy:append() {
    # Reterminal support
    # DWC2 is for the USB ports
    # I2C3 is for encryption coprocessor
    # x is for the camera
    # SPI is just for the 40 pins connector
    if [ "${ENABLE_RETERMINAL}" = "1" ]; then
        echo "# Enable reterminal" >> $CONFIG
        echo "dtoverlay=reterminal,tp_rotate=1" >> $CONFIG
        echo "dtoverlay=i2c3,pins_4_5" >> $CONFIG
        echo "dtoverlay=dwc2,dr_mode=host" >> $CONFIG
        echo "dtoverlay=vc4-kms-v3d-pi4" >> $CONFIG
        echo "dtparam=spi=on" >> $CONFIG
        echo "start_x=1" >> $CONFIG
    fi
}
