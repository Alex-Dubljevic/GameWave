{ pkgs }: {
    deps = [
        pkgs.busybox
        pkgs.nodejs-16_x
        pkgs.wget
        pkgs.graalvm17-ce
        pkgs.maven
        pkgs.replitPackages.jdt-language-server
        pkgs.replitPackages.java-debug
    ];
}