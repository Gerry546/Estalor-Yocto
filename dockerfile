FROM catthehacker/ubuntu:act-22.04

ENV LANG en_US.UTF-8
ENV LC_ALL en_US.UTF-8
ENV USER_NAME yocto
ARG host_uid=1000
ARG host_gid=1000

# Get all mickledore dependencies
RUN apt-get update -y \
    && DEBIAN_FRONTEND=noninteractive apt-get -y install \
    gawk wget git diffstat \
    unzip texinfo gcc build-essential chrpath socat cpio python3 \
    python3-pip python3-pexpect xz-utils debianutils iputils-ping \
    python3-git python3-jinja2 libegl1-mesa libsdl1.2-dev \
    python3-subunit mesa-common-dev zstd liblz4-tool file locales sudo \
    # Installs for host tooling needed for running menuconfig
    bison flex libncurses-dev tmux parted mtools dosfstools \
    # Install ssh server
    openssh-server \
    # Remove all old stuff
    && apt-get -y autoremove \
    && rm -rf /var/lib/apt/lists/* \ 
    # By default, Ubuntu uses dash as an alias for sh. Dash does not support the source command
    # needed for setting up Yocto build environments. Use bash as an alias for sh.
    && which dash &> /dev/null && (\
    echo "dash dash/sh boolean false" | debconf-set-selections && \
    DEBIAN_FRONTEND=noninteractive dpkg-reconfigure dash) || \
    echo "Skipping dash reconfigure (not applicable)" \
    # Set the locales correct
    && locale-gen en_US.UTF-8 && update-locale LC_ALL=en_US.UTF-8 LANG=en_US.UTF-8 \
    ## Create the user
    && echo "${USER_NAME} ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers.d/${USER_NAME} && \
    chmod 0440 /etc/sudoers.d/${USER_NAME} \
    # The running container writes all the build artefacts to a host directory (outside the container).
    # The container can only write files to host directories, if it uses the same user ID and
    # group ID owning the host directories. The host_uid and group_uid are passed to the docker build
    # command with the --build-arg option. By default, they are both 1000. The docker image creates
    # a group with host_gid and a user with host_uid and adds the user to the group. The symbolic
    # name of the group and user is estalor.
    && groupadd -g $host_gid $USER_NAME && useradd -g $host_gid -m -s /bin/bash -u $host_uid $USER_NAME \
    && echo ${USER_NAME}:${USER_NAME} | chpasswd

USER ${USER_NAME}

WORKDIR /
COPY sources/poky/bitbake/toaster-requirements.txt /
RUN pip3 install --user -r /toaster-requirements.txt
EXPOSE 8000