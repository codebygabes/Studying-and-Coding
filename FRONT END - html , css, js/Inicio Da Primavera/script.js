tsParticles.load("tsparticles", {
    background: {
        color: "transparent",
    },
    particles: {
        shape: {
            type: "image",
            image: {
                src: "img/folha rosa.png",
                width: 100,
                height: 100
            }
        },
        size: {
            value: { min: 10, max: 20 },
        },
        move: {
            enable: true,
            speed: 2,
            direction: "bottom",
            straight: false,
            outModes: {
                default: "out"
            }
        },
        rotate: {
            value: { min: 0, max: 360 },
            direction: "random",
            animation: {
                enable: true,
                speed: 5
            }
        },
        opacity: {
            value: { min: 0.4, max: 0.8 }
        },
        number: {
            value: 25,
        }
    }
});