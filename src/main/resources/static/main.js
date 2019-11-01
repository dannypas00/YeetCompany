import {OBJLoader2} from 'https://threejsfundamentals.org/threejs/resources/threejs/r108/examples/jsm/loaders/OBJLoader2.js';
import {MTLLoader} from 'https://threejsfundamentals.org/threejs/resources/threejs/r108/examples/jsm/loaders/MTLLoader.js';
import {MtlObjBridge} from 'https://threejsfundamentals.org/threejs/resources/threejs/r108/examples/jsm/loaders/obj2/bridge/MtlObjBridge.js';

function parseCommand(input = "") {
    return JSON.parse(input);
}

let socket;

window.onload = function () {
    let camera, scene, renderer, cameraControls;

    let worldObjects = {};

    function init() {
        camera = new THREE.PerspectiveCamera(70, window.innerWidth / window.innerHeight, 1, 1000);
        cameraControls = new THREE.OrbitControls(camera);
        camera.position.z = 15;
        camera.position.y = 5;
        camera.position.x = 15;
        cameraControls.update();
        scene = new THREE.Scene();

        renderer = new THREE.WebGLRenderer({antialias: true});
        renderer.setPixelRatio(window.devicePixelRatio);
        renderer.setSize(window.innerWidth, window.innerHeight + 5);
        renderer.shadowMap.enabled = true;
        document.body.appendChild(renderer.domElement);

        window.addEventListener('resize', onWindowResize, false);

        importModel("NewWarehouse", 500, -16, -3, 8, Math.PI);
        // var yeeterGroup = new THREE.Group();
        // importModelDynamic("Yeeter", 1, yeeterGroup);
        // scene.add(yeeterGroup);

        let light = new THREE.DirectionalLight(0x404040, 1);
        light.position.x = 10;
        light.position.y = 30;
        light.position.z = 10;
        light.intensity = 4;
        let ambLight = new THREE.AmbientLight(0x404040);
        ambLight.intensity = 0.75;
        scene.add(light);
        scene.add(ambLight);

        const skyboxLoader = new THREE.CubeTextureLoader();
        const skybox = skyboxLoader.load([
            'textures/skybox_day_side.png',
            'textures/skybox_day_side.png',
            'textures/skybox_day_top.png',
            'textures/skybox_day_bottom.png',
            'textures/skybox_day_side.png',
            'textures/skybox_day_side.png'
        ]);
        scene.background = skybox;
    }

    /**
     * Import an obj model and its corresponding mtl texture file.
     * @param name the name of the obj and mtl files to be loaded; should 100% match the files in the "models" folder except for the file extension.
     * @param size
     * @param xpos the x position to import the model at.
     * @param ypos the y position to import the model at.
     * @param zpos the z position to import the model at.
     * @param rotation the y rotation to import the model at.
     */
    async function importModel(name, size = 1, xpos = 0, ypos = 0, zpos = 0, rotation = 0) {
        const objLoader = new OBJLoader2();
        const mtlLoader = new MTLLoader();

            mtlLoader.load('models/' + name + '.mtl', async (mtlParseResult) => {
                await new Promise(resolve => {
                    let materials = MtlObjBridge.addMaterialsFromMtlLoader(mtlParseResult);
                    objLoader.addMaterials(materials);
                    objLoader.load('models/' + name + '.obj', async (root) => {
                        root.scale.set(size, size, size);
                        root.position.x = xpos;
                        root.position.y = ypos;
                        root.position.z = zpos;
                        root.rotation.y = rotation;
                        scene.add(root);
                    });
                });
            });
    }

    /**
     * Import an obj model and its corresponding mtl texture file, set the group parameter to this value.
     * @param name name the name of the obj and mtl files to be loaded; should 100% match the files in the "models" folder except for the file extension.
     * @param size the size multiplier to use for all axis.
     * @param group the THREE.Group() group to add this model to.
     */
    function importModelDynamic(name, size = 1, group) {
        const objLoader = new OBJLoader2();
        const mtlLoader = new MTLLoader();
        mtlLoader.load('models/' + name + '.mtl', (mtlParseResult) => {
            let materials = MtlObjBridge.addMaterialsFromMtlLoader(mtlParseResult);
            objLoader.addMaterials(materials);
            objLoader.load('models/' + name + '.obj', (root) => {
                root.scale.set(size, size, size);
                group.add(root);
            });
        });
    }

    function onWindowResize() {
        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();
        renderer.setSize(window.innerWidth, window.innerHeight);
    }

    function animate() {
        requestAnimationFrame(animate);
        cameraControls.update();
        renderer.render(scene, camera);
    }

    /*
     * Hier wordt de socketcommunicatie geregeld. Er wordt een nieuwe websocket aangemaakt voor het webadres dat we in
     * de server geconfigureerd hebben als connectiepunt (/connectToSimulation). Op de socket wordt een .onmessage
     * functie geregistreerd waarmee binnenkomende berichten worden afgehandeld.
     */
    socket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/connectToSimulation");
    socket.onmessage = function (event) {
        //Hier wordt het commando dat vanuit de server wordt gegeven uit elkaar gehaald
        var command = parseCommand(event.data);
        //Wanneer het commando is "object_update", dan wordt deze code uitgevoerd. Bekijk ook de servercode om dit goed te begrijpen.
        if (command.command == "object_update") {
            //Wanneer het object dat moet worden geupdate nog niet bestaat (komt niet voor in de lijst met worldObjects op de client),
            //dan wordt het 3D model eerst aangemaakt in de 3D wereld.
            if (Object.keys(worldObjects).indexOf(command.parameters.uuid) < 0) {
                //Wanneer het object een robot is, wordt de code hieronder uitgevoerd.
                console.log("command = " + command.parameters.type);
                if (command.parameters.type == "robot") {
                    let model;
                    if (command.parameters.state == "carrying") {
                        model = "YeeterCarrying";
                    }
                    else {
                        model = "Yeeter";
                    }
                    // console.log("help im a robot");
                    // var geometry = new THREE.BoxGeometry(0.9, 0.3, 0.9);
                    // var cubeMaterials = [
                    //     new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_side.png"), side: THREE.DoubleSide }), //LEFT
                    //     new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_side.png"), side: THREE.DoubleSide }), //RIGHT
                    //     new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_top.png"), side: THREE.DoubleSide }), //TOP
                    //     new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_bottom.png"), side: THREE.DoubleSide }), //BOTTOM
                    //     new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_front.png"), side: THREE.DoubleSide }), //FRONT
                    //     new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_front.png"), side: THREE.DoubleSide }), //BACK
                    // ];
                    // var material = new THREE.MeshFaceMaterial(cubeMaterials);
                    // var robot = new THREE.Mesh(geometry, material);

                    // var yeeterGroup = new THREE.Group();
                    // importModelDynamic("Yeeter", 1, yeeterGroup);
                    // scene.add(yeeterGroup);

                    let robots = new THREE.Group();
                    importModelDynamic(model, 1, robots);
                    robots.position.y = 2;
                    scene.add(robots);
                    worldObjects[command.parameters.uuid] = robots;
                }
                if (command.parameters.type == "minecart") {
                    var geometry = new THREE.BoxGeometry(1.2, 0.7, 1.1);
                    var cubeMaterials = [
                        new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/minecartside.png"), side: THREE.DoubleSide }), //LEFT
                        new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/minecartside.png"), side: THREE.DoubleSide }), //RIGHT
                        new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/minecarttop.png"), side: THREE.DoubleSide }), //TOP
                        new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/minecartbottom.png"), side: THREE.DoubleSide }), //BOTTOM
                        new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/minecartside.png"), side: THREE.DoubleSide }), //FRONT
                        new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/minecartside.png"), side: THREE.DoubleSide }), //BACK
                    ];
                    var material = new THREE.MeshFaceMaterial(cubeMaterials);
                    var minecart = new THREE.Mesh(geometry, material);
                    minecart.position.y = 0.15;

                    var group = new THREE.Group();
                    group.add(minecart);

                    scene.add(group);
                    worldObjects[command.parameters.uuid] = group;
                }
            }
            /*
             * Deze code wordt elke update uitgevoerd. Het update alle positiegegevens van het 3D object.
             */
            var object = worldObjects[command.parameters.uuid];
            object.position.x = command.parameters.x;
            object.position.z = command.parameters.z;
            object.rotation.y = command.parameters.rotationY;
        }
    }
    init();
    animate();
}