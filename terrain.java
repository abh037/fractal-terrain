float[] vertices = new float[3*(int)Math.pow(Math.pow(2, n) + 1, 2)];
float[] colors = new float[3*(int)Math.pow(Math.pow(2, n) + 1, 2)];
int[] indices = new int[6*(int)Math.pow(Math.pow(2, n) + 1, 2)];

public void Fractal(int n, float smoothness) {

	int idx = 0;

	float[][] map = new float[(int)Math.pow(2, n) + 1][(int)Math.pow(2, n) + 1];
	map[0][0] = 0f;
	map[0][map.length - 1] = 0f;
	map[map.length - 1][0] = 0f;
	map[map.length - 1][map.length - 1] = 0f;

	map = diamondSquare(smoothness, map, new int[]{0, 0}, new int[]{map.length - 1, 0}, new int[]{0, map.length - 1}, new int[]{map.length - 1, map.length - 1});

	for (int i = 0; i < map.length; i++) {
		for (int j = 0; j < map.length; j++) {

			float x = 3*((float)i - (map.length/2));
			float y = map[i][j];
			float z = 3*((float)j - (map.length/2));

			vertices[3*(i*(int)map.length + j) + 0] = x;
			vertices[3*(i*(int)map.length + j) + 1] = y;
			vertices[3*(i*(int)map.length + j) + 2] = z;

			colors[3*(i*(int)map.length + j) + 0] = .5f;
			colors[3*(i*(int)map.length + j) + 1] = y / 2;
			colors[3*(i*(int)map.length + j) + 2] = y / 2;

		}
	}

	for (int i = 0; i < map.length - 1; i++) {
		for (int j = 0; j < map.length - 1; j++) {
			indices[6*(i*(int)map.length + j) + 0] = i*map.length + j;
			indices[6*(i*(int)map.length + j) + 1] = i*map.length + j + 1;
			indices[6*(i*(int)map.length + j) + 2] = i*map.length + j + map.length;
			indices[6*(i*(int)map.length + j) + 3] = i*map.length + j + 1;
			indices[6*(i*(int)map.length + j) + 4] = i*map.length + j + map.length;
			indices[6*(i*(int)map.length + j) + 5] = i*map.length + j + map.length + 1;

		}
	}		
}
		
public float[][] diamondSquare(float smoothness, float[][] arr, int[] left_t, int[] right_t, int[] left_b, int[] right_b) {

  if (right_t[0] - left_t[0] > 1) {

    int[] mid_t = {(int)(left_t[0] + right_t[0]) / 2, (int)(left_t[1] + right_t[1]) / 2};
    int[] mid_r = {(int)(right_b[0] + right_t[0]) / 2, (int)(right_b[1] + right_t[1]) / 2};
    int[] mid_l = {(int)(left_t[0] + left_b[0]) / 2, (int)(left_t[1] + left_b[1]) / 2};
    int[] mid_b = {(int)(left_b[0] + right_b[0]) / 2, (int)(left_b[1] + right_b[1]) / 2};
    int[] true_mid = {mid_t[0], mid_r[1]};

    arr[mid_t[0]][mid_r[1]] = (arr[left_t[0]][left_t[1]] + arr[right_t[0]][right_t[1]] + arr[left_b[0]][left_b[1]] + arr[right_b[0]][right_b[1]]) / 4;

    arr[mid_t[0]][mid_t[1]] = (1 / smoothness)*((float)Math.random() - .5f) + (arr[left_t[0]][left_t[1]] + arr[right_t[0]][right_t[1]] + arr[true_mid[0]][true_mid[1]]) / 3;
    arr[mid_b[0]][mid_b[1]] = (1 / smoothness)*((float)Math.random() - .5f) + (arr[left_b[0]][left_b[1]] + arr[right_b[0]][right_b[1]] + arr[true_mid[0]][true_mid[1]]) / 3;
    arr[mid_l[0]][mid_l[1]] = (1 / smoothness)*((float)Math.random() - .5f) + (arr[left_t[0]][left_t[1]] + arr[left_b[0]][left_b[1]] + arr[true_mid[0]][true_mid[1]]) / 3;
    arr[mid_r[0]][mid_r[1]] = (1 / smoothness)*((float)Math.random() - .5f) + (arr[right_t[0]][right_t[1]] + arr[right_b[0]][right_b[1]] + arr[true_mid[0]][true_mid[1]]) / 3;

    arr = diamondSquare(smoothness, arr, left_t, mid_t, mid_l, true_mid);
    arr = diamondSquare(smoothness, arr, mid_t, right_t, true_mid, mid_r);
    arr = diamondSquare(smoothness, arr, mid_l, true_mid, left_b, mid_b);
    arr = diamondSquare(smoothness, arr, true_mid, mid_r, mid_b, right_b);

  }

  return arr;

}
