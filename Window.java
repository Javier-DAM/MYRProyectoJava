import Assets.Assets;
import input.Teclado;
import objects.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import Menu.Menu;

public class Window extends JFrame implements Runnable {

    public static final int width = 1920, height = 1080;
    private Canvas canvas;
    private Thread thread;
    private boolean running = false;
    private Graphics g;
    private BufferStrategy bs;
    private Teclado teclado;
    private Jugador jugador1, jugador2;
    private Enemigos enemigo1, enemigo2, enemigo3, enemigo4;
    private List<Enemigos> enemigosList;
    private EstadoDelJuego estadoDelJuego;
    private int averagefps;
    private double delta = 0;
    private double targetTime = 1000000000.0 / 60.0; // 60 FPS

    private boolean j1orientacion = true, j2orientacion = false;
    private boolean isAttacking1 = false, isBlocking1 = false;
    private boolean isAttacking2 = false, isBlocking2 = false;
    private int attackIndex1 = 0, attackIndex2 = 0;
    private int dialogueIndex1 = 0, dialogueIndex2 = 0;
    private long attackLastTime1 = 0, attackLastTime2 = 0;
    private int walkIndex1 = 0, idleIndex1 = 0;
    private int walkIndex2 = 0, idleIndex2 = 0;
    private long lastTime = System.currentTimeMillis();
    private long delay = 100;
    private int attackDelay = 100;
    private long lastEnemySpawnTime = 0;
    private long enemySpawnDelay = 500;

    //Para pausar el juego
    private Menu menu;

    public Window() {
        setTitle("RSG");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(true);

        teclado = new Teclado();
        canvas.addKeyListener(teclado);
        add(canvas);

        setVisible(true);
    }

    public void iniciarJuego() {
         this.start();
    }

    private void update() {
        teclado.update();
        menu.update();

        if (menu.isPaused()) {
            return;
        }

        // Spawneo infinito de enemigos
        if (System.currentTimeMillis() - lastEnemySpawnTime >= enemySpawnDelay) {
            spawnEnemigo();
            lastEnemySpawnTime = System.currentTimeMillis();
        }



        jugador1.updateJugador1();
        jugador2.updateJugador2();
        estadoDelJuego.update();

        long currentTime = System.currentTimeMillis();

        //Actualizar enemigos
        for (Enemigos enemigo : enemigosList) {
            enemigo.setIsAttacking1(isAttacking1);
            enemigo.setIsAttacking2(isAttacking2);
            enemigo.setIsBlocking1(isBlocking1);  // Añade esto
            enemigo.setIsBlocking2(isBlocking2);  // Añade esto
            enemigo.update();
        }



        // MONA (Jugador 1)
        if (jugador1.getSalud() > 0) {
            if (Teclado.atacar) {
                if (!isAttacking1) {
                    isAttacking1 = true;
                    attackIndex1 = 0;
                    attackLastTime1 = currentTime;
                }
            } else if (Teclado.bloquear) {
                isBlocking1 = true;
                isAttacking1 = false;
            } else {
                isBlocking1 = false;
                if (!Teclado.atacar) {
                    isAttacking1 = false;
                    attackIndex1 = 0;
                }
            }

            if (isAttacking1 && currentTime - attackLastTime1 > attackDelay) {
                attackIndex1++;
                if (attackIndex1 >= Assets.monaAttack.length) {
                    isAttacking1 = false;
                    attackIndex1 = 0;
                }
                attackLastTime1 = currentTime;
            }

            if (Teclado.derecha) j1orientacion = true;
            else if (Teclado.izquierda) j1orientacion = false;
        } else {
            // Jugador muerto: no controles
            isAttacking1 = false;
            isBlocking1 = false;
        }

// RONA (Jugador 2)
        if (jugador2.getSalud() > 0) {
            if (Teclado.atacar2) {
                if (!isAttacking2) {
                    isAttacking2 = true;
                    attackIndex2 = 0;
                    attackLastTime2 = currentTime;
                }
            } else if (Teclado.bloquear2) {
                isBlocking2 = true;
                isAttacking2 = false;
            } else {
                isBlocking2 = false;
                if (!Teclado.atacar2) {
                    isAttacking2 = false;
                    attackIndex2 = 0;
                }
            }

            if (isAttacking2 && currentTime - attackLastTime2 > attackDelay) {
                attackIndex2++;
                if (attackIndex2 >= Assets.ronaAttack.length) {
                    isAttacking2 = false;
                    attackIndex2 = 0;
                }
                attackLastTime2 = currentTime;
            }

            if (Teclado.derecha2) j2orientacion = true;
            else if (Teclado.izquierda2) j2orientacion = false;
        } else {
            // Jugador muerto: no controles
            isAttacking2 = false;
            isBlocking2 = false;
        }


        // ACTUALIZACIÓN DE ANIMACIONES
        if (currentTime - lastTime > delay) {
            if (!isAttacking1 && !isBlocking1 && (Teclado.arriba || Teclado.abajo || Teclado.izquierda || Teclado.derecha)) {
                walkIndex1 = (walkIndex1 + 1) % Assets.monaWalk.length;
            } else {
                idleIndex1 = (idleIndex1 + 1) % Assets.monaIdle.length;
            }

            if (!isAttacking2 && !isBlocking2 && (Teclado.arriba2 || Teclado.abajo2 || Teclado.izquierda2 || Teclado.derecha2)) {
                walkIndex2 = (walkIndex2 + 1) % Assets.ronaWalk.length;
            } else {
                idleIndex2 = (idleIndex2 + 1) % Assets.ronaIdle.length;
            }
            lastTime = currentTime;
        }

        if (jugador1.getSalud() <= 0 && jugador2.getSalud() <= 0) {
            menu.setJuegoTerminado(true);
            menu.updateMuerte();

            if (menu.isReiniciarSolicitado()) {
                reiniciarJuego(); // Este método lo crearás abajo
                menu.resetReiniciarSolicitado();
            }
        }
    }

    private void draw() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        g.setColor(new Color(60, 80, 40));
        g.fillRect(0, 0, width, height);

        if (menu.isPaused()) {
            menu.draw(g);
            g.dispose();
            bs.show();
            return;
        }

        if (menu.isJuegoTerminado()) {
            menu.drawMuerte(g, jugador1, jugador2);
            g.dispose();
            bs.show();
            return;
        }


        // Crear una lista de objetos dibujables ordenables por Y
        List<ObjetoJuego> entidadesOrdenadas = new ArrayList<>();
        entidadesOrdenadas.add(jugador1);
        entidadesOrdenadas.add(jugador2);
        entidadesOrdenadas.addAll(enemigosList);

        entidadesOrdenadas.sort(Comparator.comparingDouble(o -> o.getPosicion().getY()));

        // Dibujar en orden de Y
        for (ObjetoJuego obj : entidadesOrdenadas) {
            if (obj instanceof Jugador jugador) {
                int x = (int) jugador.getPosicion().getX();
                int y = (int) jugador.getPosicion().getY();
                boolean orientacion = (jugador == jugador1) ? j1orientacion : j2orientacion;
                boolean atacando = (jugador == jugador1) ? isAttacking1 : isAttacking2;
                boolean bloqueando = (jugador == jugador1) ? isBlocking1 : isBlocking2;
                int ataqueIndex = (jugador == jugador1) ? attackIndex1 : attackIndex2;
                int idleIndex = (jugador == jugador1) ? idleIndex1 : idleIndex2;
                int walkIndex = (jugador == jugador1) ? walkIndex1 : walkIndex2;
                int dialogueIndex = (jugador == jugador1) ? dialogueIndex1 : dialogueIndex2;

                if (jugador.getSalud() < 0) {
                    g.drawImage(orientacion
                                    ? (jugador == jugador1 ? Assets.monaDialogue[dialogueIndex] : Assets.ronaDialogue[dialogueIndex])
                                    : (jugador == jugador1 ? Assets.monaDialogueFlipped[dialogueIndex] : Assets.ronaDialogueFlipped[dialogueIndex]),
                            x, y, null);
                } else {
                    if (atacando) {
                        g.drawImage(orientacion
                                        ? (jugador == jugador1 ? Assets.monaAttack[ataqueIndex] : Assets.ronaAttack[ataqueIndex])
                                        : (jugador == jugador1 ? Assets.monaAttackFlipped[ataqueIndex] : Assets.ronaAttackFlipped[ataqueIndex]),
                                x, y, null);
                    } else if (bloqueando) {
                        g.drawImage(orientacion
                                        ? (jugador == jugador1 ? Assets.monaProtection[1] : Assets.ronaProtection[1])
                                        : (jugador == jugador1 ? Assets.monaProtectionFlipped[1] : Assets.ronaProtectionFlipped[1]),
                                x, y, null);
                    } else if ((jugador == jugador1 && (Teclado.arriba || Teclado.abajo || Teclado.izquierda || Teclado.derecha)) ||
                            (jugador == jugador2 && (Teclado.arriba2 || Teclado.abajo2 || Teclado.izquierda2 || Teclado.derecha2))) {
                        g.drawImage(orientacion
                                        ? (jugador == jugador1 ? Assets.monaWalk[walkIndex] : Assets.ronaWalk[walkIndex])
                                        : (jugador == jugador1 ? Assets.monaWalkFlipped[walkIndex] : Assets.ronaWalkFlipped[walkIndex]),
                                x, y, null);
                    } else {
                        g.drawImage(orientacion
                                        ? (jugador == jugador1 ? Assets.monaIdle[idleIndex] : Assets.ronaIdle[idleIndex])
                                        : (jugador == jugador1 ? Assets.monaIdleFlipped[idleIndex] : Assets.ronaIdleFlipped[idleIndex]),
                                x, y, null);
                    }
                }

            } else {
                obj.draw(g); // Enemigo u otro objeto
            }
        }

        textos();
        estadoDelJuego.draw(g);
        g.dispose();
        bs.show();
    }


    private void textos() {
        int vidaP1 = jugador1.getSalud();
        int vidaP2 = jugador2.getSalud();

        //Posicones en enteros
        int x1 = (int) jugador1.getPosicion().getX();
        int y1 = (int) jugador1.getPosicion().getY();
        int x2 = (int) jugador2.getPosicion().getX();
        int y2 = (int) jugador2.getPosicion().getY();

        g.setColor(Color.WHITE);
        g.setFont(new Font("Tahoma", Font.BOLD, 10));
        g.drawString("FPS: " + averagefps, 10, 10);
        g.setFont(new Font("Tahoma", Font.BOLD, 15));
        g.drawString("Jugador 1: Caminar: WASD Atacar: V Bloquear: B", 10, 30);
        g.drawString("Jugador 2: Caminar: ↑↓→← Atacar: , Bloquear: .", 10, 50);
        g.setFont(new Font("Arial", Font.BOLD, 15));

        if (vidaP1 > 0){
            g.drawString( "Vida"+vidaP1, x1, y1);
        }else {
            g.drawString( "Jugador 1 muerto", x1, y1);
        }
        if (vidaP2 > 0){
            g.drawString( "Vida"+vidaP2, x2, y2);
        }else {
            g.drawString( "Jugador 2 muerto", x2, y2);
        }
    }

    private void init() {
        menu = new Menu(teclado);
        Assets.init();
        estadoDelJuego = new EstadoDelJuego();

        // Centrar jugadores
        jugador1 = new Jugador(1, new Vector2D(width / 2 - 32, height / 2 - 32), Assets.monaIdle);
        jugador2 = new Jugador(2, new Vector2D(width / 2 + 32, height / 2 - 32), Assets.ronaIdle);

        enemigosList = new ArrayList<>();

        Random rand = new Random();
        int cantidadEnemigos = 10;
        double distanciaMinima = 80.0;
        int intentosMaximos = 100;

        float maxVelocidadEnemigos = Math.min(jugador1.getVelocidad(), jugador2.getVelocidad()); // 5.0

        for (int i = 0; i < cantidadEnemigos; ) {
            boolean posicionValida = true;
            Vector2D nuevaPos = new Vector2D(rand.nextInt(750), rand.nextInt(550));

            for (Enemigos otro : enemigosList) {
                double dx = nuevaPos.getX() - otro.getPosicion().getX();
                double dy = nuevaPos.getY() - otro.getPosicion().getY();
                if (Math.hypot(dx, dy) < distanciaMinima) {
                    posicionValida = false;
                    break;
                }
            }

            if (posicionValida) {
                Enemigos enemigo;

                if (i % 2 == 0) {
                    enemigo = new Enemigos(nuevaPos, Assets.foxyIdle);
                    enemigo.setWalkSprites(Assets.foxyWalk, Assets.foxyWalkFlipped);
                } else {
                    enemigo = new Enemigos(nuevaPos, Assets.jellyIdle1);
                    enemigo.setWalkSprites(Assets.jellyWalk, Assets.jellyWalkFlipped);
                }

                // Velocidad aleatoria entre 1.0 y menos que la velocidad del jugador
                float velocidadAleatoria = 1.0f + rand.nextFloat() * (maxVelocidadEnemigos - 1.0f);
                enemigo.setSpeed(velocidadAleatoria);

                enemigo.setJugador1(jugador1);
                enemigo.setJugador2(jugador2);

                enemigosList.add(enemigo);
                i++;
            } else {
                intentosMaximos = intentosMaximos;
                if (intentosMaximos <= 0) {
                    System.out.println("No se pudieron colocar todos los enemigos sin superposición.");
                    break;
                }
            }
        }
    }

    private void reiniciarJuego() {
        // Reiniciar jugadores
        jugador1 = new Jugador(1, new Vector2D(width / 2 - 32, height / 2 - 32), Assets.monaIdle);
        jugador2 = new Jugador(2, new Vector2D(width / 2 + 32, height / 2 - 32), Assets.ronaIdle);

        // Reiniciar enemigos
        enemigosList.clear();
        lastEnemySpawnTime = System.currentTimeMillis();

        // Reset del estado del juego y menú
        estadoDelJuego = new EstadoDelJuego();
        menu.setJuegoTerminado(false);
    }


    @Override
    public void run() {
        long now;
        long lastTime = System.nanoTime();
        int frames = 0;
        int time = 0;

        init();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / targetTime;
            time += (now - lastTime);
            lastTime = now;

            if (delta >= 1) {
                update();
                draw();
                delta--;
                frames++;
            }

            if (time >= 1000000000) {
                averagefps = frames;
                frames = 0;
                time = 0;
            }
        }
        stop();
    }

    private void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void spawnEnemigo() {
        Random rand = new Random();
        Vector2D nuevaPos = new Vector2D(rand.nextInt(750), rand.nextInt(550));

        Enemigos enemigo;
        if (rand.nextBoolean()) {
            enemigo = new Enemigos(nuevaPos, Assets.foxyIdle);
            enemigo.setWalkSprites(Assets.foxyWalk, Assets.foxyWalkFlipped);
        } else {
            enemigo = new Enemigos(nuevaPos, Assets.jellyIdle1);
            enemigo.setWalkSprites(Assets.jellyWalk, Assets.jellyWalkFlipped);
        }

        enemigo.setJugador1(jugador1);
        enemigo.setJugador2(jugador2);

        float velocidadAleatoria = 1.0f + rand.nextFloat() * 2.0f; // velocidad aleatoria
        enemigo.setSpeed(velocidadAleatoria);

        enemigosList.add(enemigo);
    }

}
