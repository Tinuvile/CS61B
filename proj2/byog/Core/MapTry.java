package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class MapTry {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    public static final long SEED = 2873173;
    public static boolean MODE = false;
    public static Random RANDOM;

    public static final int InsideDoor = 0;
    public static final int OutsideDoorLand = 1;
    public static final int OutsideDoorWater = 2;

    /* {coordinateX, coordinateY, chooseWayDirection}
       chooseWayDirection:
                0
             1     2
                3
    */
    public static ArrayList<Integer[]> DoorForHallways = new ArrayList<>();

    public static TETile randomTETile(int mode) {
        if (mode == InsideDoor) {
            int tileNum = RANDOM.nextInt(4);
            return switch (tileNum) {
                case 0 -> Tileset.WALL;
                case 1 -> Tileset.FLOOR;
                case 2 -> Tileset.LOCKED_DOOR;
                case 3 -> Tileset.UNLOCKED_DOOR;
                default -> Tileset.NOTHING;
            };
        } else if (mode == OutsideDoorLand) {
            int tileNum = RANDOM.nextInt(5);
            return switch (tileNum) {
                case 0 -> Tileset.FLOWER;
                case 1 -> Tileset.GRASS;
                case 2 -> Tileset.TREE;
                case 3 -> Tileset.SAND;
                case 4 -> Tileset.MOUNTAIN;
                default -> Tileset.NOTHING;
            };
        } else if (mode == OutsideDoorWater) {
            int tileNum = RANDOM.nextInt(2);
            return switch (tileNum) {
                case 0 -> Tileset.WATER;
                case 1 -> Tileset.SAND;
                default -> Tileset.NOTHING;
            };
        } else {
            return Tileset.NOTHING;
        }
    }

    /**
     * TODO optimize the problem of room overlap
     */
    public static void CreateSquareRoom(TETile[][] map) {
        int height = map[0].length;
        int width = map.length;

        // keep the number of the rooms between 5 and 10
        int RoomNum = 8 + RANDOM.nextInt(6);
        // choose a door
        int Door = 1 + RANDOM.nextInt(RoomNum + 1);
        int UnlockedDoor = 1 + RANDOM.nextInt(RoomNum + 1);
        for (int i = 1; i <= RoomNum; i++) {
            int xNum = 4 + RANDOM.nextInt(6);    // the width of the room
            int yNum = 3 + RANDOM.nextInt(5);    // the length of the room
            int x = 5 + RANDOM.nextInt(65);      // the coordinate x of the room
            int y = 5 + RANDOM.nextInt(15);      // the coordinate y of the room
            // build the wall
            for (int m = x; m < x + xNum; m++) {
                if (map[m][y] != Tileset.FLOOR) {
                    map[m][y] = Tileset.WALL;
                }
                if (map[m][y + yNum - 1] != Tileset.FLOOR){
                    map[m][y + yNum - 1] = Tileset.WALL;
                }
            }
            for (int n = y; n < y + yNum; n++) {
                if (map[x][n] != Tileset.FLOOR) {
                    map[x][n] = Tileset.WALL;
                }
                if (map[x + xNum - 1][n] != Tileset.FLOOR) {
                    map[x + xNum - 1][n] = Tileset.WALL;
                }
            }
            // build the floor
            for (int m = x + 1; m < x + xNum - 1; m++) {
                for (int n = y + 1; n < y + yNum - 1; n++) {
                    map[m][n] = Tileset.FLOOR;
                }
            }
            // build a way to other rooms
            int chooseWay = RANDOM.nextInt(4);
            boolean WAY = true;
            while (WAY) {
                if (chooseWay == 0) {
                    int doorPos = x + 1 + RANDOM.nextInt(xNum - 2);
                    if (map[doorPos][y] == Tileset.WALL) {
                        map[doorPos][y] = Tileset.FLOOR;
                        DoorForHallways.add(new Integer[]{doorPos, y ,chooseWay});
                        WAY = false;
                    }
                } else if (chooseWay == 1) {
                    int doorPos = y + 1 + RANDOM.nextInt(yNum - 2);
                    if (map[x][doorPos] == Tileset.WALL) {
                        map[x][doorPos] = Tileset.FLOOR;
                        DoorForHallways.add(new Integer[]{x, doorPos, chooseWay});
                        WAY = false;
                    }
                } else if (chooseWay == 2) {
                    int doorPos = y + 1 + RANDOM.nextInt(yNum - 2);
                    if (map[x + xNum - 1][doorPos] == Tileset.WALL) {
                        map[x + xNum - 1][doorPos] = Tileset.FLOOR;
                        DoorForHallways.add(new Integer[]{x + xNum - 1, doorPos, chooseWay});
                        WAY = false;
                    }
                } else {
                    int doorPos = x + 1 + RANDOM.nextInt(xNum - 2);
                    if (map[doorPos][y + yNum - 1] == Tileset.WALL) {
                        map[doorPos][y + yNum - 1] = Tileset.FLOOR;
                        DoorForHallways.add(new Integer[]{doorPos, y + yNum - 1, chooseWay});
                        WAY = false;
                    }

                }
            }
            // build a door
            if (i == Door) {
                boolean DO = true;
                while (DO) {
                    int choose = RANDOM.nextInt(4);
                    if (choose == 0) {
                        int doorPos = x + 1 + RANDOM.nextInt(xNum - 2);
                        if (map[doorPos][y] != Tileset.FLOOR) {
                            map[doorPos][y] = Tileset.LOCKED_DOOR;
                            DO = false;
                        }
                    } else if (choose == 1) {
                        int doorPos = y + 1 + RANDOM.nextInt(yNum - 2);
                        if (map[x][doorPos] != Tileset.FLOOR) {
                            map[x][doorPos] = Tileset.LOCKED_DOOR;
                            DO = false;
                        }

                    } else if (choose == 2) {
                        int doorPos = y + 1 + RANDOM.nextInt(yNum - 2);
                        if (map[x + xNum - 1][doorPos] != Tileset.FLOOR) {
                            map[x + xNum - 1][doorPos] = Tileset.LOCKED_DOOR;
                            DO = false;
                        }

                    } else {
                        int doorPos = x + 1 + RANDOM.nextInt(xNum - 2);
                        if (map[doorPos][y + yNum - 1] != Tileset.FLOOR) {
                            map[doorPos][y + yNum - 1] = Tileset.LOCKED_DOOR;
                            DO = false;
                        }

                    }
                }
            }
            if (i == UnlockedDoor) {
                boolean DO = true;
                while (DO) {
                    int choose = RANDOM.nextInt(4);
                    if (choose == 0) {
                        int doorPos = x + 1 + RANDOM.nextInt(xNum - 2);
                        if (map[doorPos][y] != Tileset.FLOOR) {
                            map[doorPos][y] = Tileset.UNLOCKED_DOOR;
                            DO = false;
                        }
                    } else if (choose == 1) {
                        int doorPos = y + 1 + RANDOM.nextInt(yNum - 2);
                        if (map[x][doorPos] != Tileset.FLOOR) {
                            map[x][doorPos] = Tileset.UNLOCKED_DOOR;
                            DO = false;
                        }

                    } else if (choose == 2) {
                        int doorPos = y + 1 + RANDOM.nextInt(yNum - 2);
                        if (map[x + xNum - 1][doorPos] != Tileset.FLOOR) {
                            map[x + xNum - 1][doorPos] = Tileset.UNLOCKED_DOOR;
                            DO = false;
                        }

                    } else {
                        int doorPos = x + 1 + RANDOM.nextInt(xNum - 2);
                        if (map[doorPos][y + yNum - 1] != Tileset.FLOOR) {
                            map[doorPos][y + yNum - 1] = Tileset.UNLOCKED_DOOR;
                            DO = false;
                        }

                    }
                }
            }
        }
    }

    /**
     * now I have some rooms and every room have one 'door' for hallway.
     * the CreateHallway need to connect these 'doors'.
     * TODO each door need to connect with every other doors.
     */
    public static void CreateHallway(TETile[][] map) {
        // make sure the position of the door could connect to other places
        /*
        for (int i = 0; i < DoorForHallways.size() - 1; i++) {
            Integer[] doorPos = DoorForHallways.get(i);
        }
         */
        // connect different doors.
        for (int i = 0; i < DoorForHallways.size() - 1; i++) {
            Integer[] doorA = DoorForHallways.get(i);
            for (int j = i + 1; j < DoorForHallways.size() - 1; j++) {
                Integer[] doorB = DoorForHallways.get(j);
                /* path generation: my idea is to start with the coordinates, we assume
                  that paths to the right and down are positive, and the paths to the left
                  and up are negative. So the sum of up and down should be constant, equal
                  to delta y (the different value of the two points' vertical coordinates),
                  the same as the sum of left and right. We can use a random number to control
                  the complexity of the path: how many vertical and horizontal paths, also use
                  random numbers to control the length of these paths except the last one. It
                  should be calculated.
                 */
                int deltaX = doorA[0] - doorB[0];
                int deltaY = doorA[1] - doorB[1];
                if (doorA[2] == 0) {
                    // now I want to find two other points which can move four directions.
                }
            }
        }
    }

    public static void FillWithRandomTETile(TETile[][] map) {
        CreateSquareRoom(map);
        CreateHallway(map);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        if (MODE) {
            RANDOM = new Random(SEED);
        } else {
            long seed = System.currentTimeMillis();
            RANDOM = new Random(seed);
        }

        TETile[][] MapTryTiles = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                MapTryTiles[x][y] = Tileset.NOTHING;
            }
        }

        FillWithRandomTETile(MapTryTiles);

        ter.renderFrame(MapTryTiles);
    }
}

















