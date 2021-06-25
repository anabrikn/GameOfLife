package life;

import java.util.Random;

class Universe {
    private Cell[][] previousGeneration; //
    private int countGen; // номер текущего поколения
    private int sizeUniverse; // размер вселенной

    // конструктор устанавливает размер вселенной и генерирует вселенную
    public Universe() {
        this.sizeUniverse = 25; //
        this.countGen = 0;
        createUniverse(); // генерируем вселенную
    }

    // конструктор устанавливает размер вселенной и генерирует вселенную
    public Universe(int integer) {
        this.sizeUniverse = integer; //
        this.countGen = 0;
        createUniverse(); // генерируем вселенную
    }

    public Cell[][] getCurrentGeneration() {
        return previousGeneration;
    }

    public int getCountGen() {
        return countGen;
    }

    // возвращает количество живых клеток в текущем поколении
    protected int checkAliveCell() {
        int aliveCell = 0;
        for (int i = 0; i < this.previousGeneration.length; i++) {
            for (int j = 0; j < this.previousGeneration[i].length; j++) {
                if (previousGeneration[i][j].isAlive()) {
                    aliveCell++;
                }
            }
        }
        return aliveCell;
    }

    // Метод, генерирующий следующую ступень развития вселенной (проверка по соседям)
    void nextGeneration() {
        Cell[][] newGeneration = new Cell[this.previousGeneration.length][this.previousGeneration[0].length]; // новый массив для следующего поколения

        for (int i = 0; i < this.previousGeneration.length; i++) {
            for (int j = 0; j < this.previousGeneration[0].length; j++) {
                // обойти оригинальный массив, для каждой клетки вызвать метод проверяющий соседей и внести результат в аналогичную клетку нового массива
                newGeneration[i][j] = new Cell(checkCell(i, j));
            }
        }
        this.countGen++;
        this.previousGeneration = newGeneration;
    }

    // Метод, возвращающий результат boolean - жива клетка в следующем поколении или нет
    // принимает координаты ячейки, судьбу которой нужно опреелить для следующего поколения
    private boolean checkCell(int i, int j) {
        boolean result = false;
        int counter = countAliveNeighbors(i, j); // счетчик живых соседей клетки

        // на основании количества соседей определить будет ли жива клетка в следующем поколении
        if (previousGeneration[i][j].isAlive()) { // если клетка жива
            if (counter >= 2 && counter <= 3) {
                result = true;
            }
        } else if (!previousGeneration[i][j].isAlive()) { // если клетка мертва
            if (counter == 3) {
                result = true;
            }
        }

        return result;
    }

    // Метод, проверяющий соседей ячейки и возвращающий количество живых соседей
    // принимает координаты ячейки, соседей которой нужно проверить
    private int countAliveNeighbors(int i, int j) {
        int counter = 0; // счетчик живых соседей клетки

        // посчитываем количество живых соседей ячейки
        int rowPosition = i - 1 < 0 ? previousGeneration.length - 1 : i - 1; // + если предыдущая позиция выходит за границу начала массива
        int columnPosition = j - 1 < 0 ? previousGeneration.length - 1 : j - 1; // +
        for (int k = 0; k < 3; k++) { // проход по строкам
            for (int m = 0; m < 3; m++) { // проход по ячейкам строки

                if (i == rowPosition && j == columnPosition) { // если клетка центральная (та, чьих соседей проверяют)
                    columnPosition = columnPosition == previousGeneration.length - 1 ? 0 : columnPosition + 1;
                    continue;
                }

                if (previousGeneration[rowPosition][columnPosition].isAlive()) { // если клетка живая увеличиваем счетчик
                    counter++;
                }
                columnPosition = columnPosition == previousGeneration.length - 1 ? 0 : columnPosition + 1; // переходим к следующей клетке в строке
            }
            columnPosition = j - 1 < 0 ? previousGeneration.length - 1 : j - 1; // возвращаем "счетчик ячеек в строке" к началу
            rowPosition = rowPosition == previousGeneration[0].length - 1 ? 0 : rowPosition + 1; // переводим счетчик к следующей строке
        }

        return counter;
    }

    // Метод, генерирующий новую вселенную (первый раз) заполняет клетками ячейки, в которых генератор сгенерировал true
    private void createUniverse() {
        Cell[][] arr = new Cell[sizeUniverse][sizeUniverse]; // создание двумерного массива для хранения состояния вселенной
        Random random = new Random(); // создание рандомного числа
        boolean b;
        for (int i = 0; i < arr.length; i++) { // заполнение состояния вселенной
            for (int j = 0; j < arr[i].length; j++) {
                b = random.nextBoolean();
                if (b == true) {
                    arr[i][j] = new Cell(true);
                } else {
                    arr[i][j] = new Cell(false);
                }
            }
        }
        this.previousGeneration = arr;
    }


    // Метод, отрисовывающий вселенную
    public void printCurrentUniverse() {
        for (int i = 0; i < previousGeneration.length; i++) { // обход вывод состояния вселенной
            for (int j = 0; j < previousGeneration[i].length; j++) {
                if (previousGeneration[i][j].isAlive()) {
                    System.out.print("O");
                } else {
                    System.out.print(" ");
                }

            }
            System.out.println();
        }
    }
}