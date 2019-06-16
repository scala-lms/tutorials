/*****************************************
Emitting C Generated Code
*******************************************/
#include <unistd.h>
#include <errno.h>
#include <err.h>
#include <string.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <fcntl.h>
#include <mpi.h>
#include <stdio.h>
#include <stdint.h>
#include <sys/mman.h>
#include <stdbool.h>
/************* Functions **************/
#ifndef MAP_FILE
#define MAP_FILE MAP_SHARED
#endif
int fsize(int fd) {
  struct stat stat;
  int res = fstat(fd,&stat);
  return stat.st_size;
}
int printll(char* s) {
  while (*s != '\n' && *s != ',' && *s != '\t') {
    putchar(*s++);
  }
  return 0;
}
long hash(char *str0, int len) {
  unsigned char* str = (unsigned char*)str0;
  unsigned long hash = 5381;
  int c;
  while ((c = *str++) && len--)
  hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
  return hash;
}
/**************** Snippet ****************/
void Snippet(char* x0) {
  int argc = 0; char** argv = (char**)malloc(0); int provided;
  MPI_Init_thread(&argc, &argv, MPI_THREAD_FUNNELED, &provided);
  int x1 = 0;
  MPI_Comm_size(MPI_COMM_WORLD, &x1);
  int x2 = 0;
  MPI_Comm_rank(MPI_COMM_WORLD, &x2);
  MPI_Request req;
  MPI_Status status;
  int x3 = x2;
  int x4 = x1;
  int x5 = 78 / x4 + 1;
  int* x6 = (int*)malloc(256 * sizeof(int));
  int x7 = 0;
  while (x7 != 256) {
    x6[x7] = 0;
    x7 = x7 + 1;
  }
  int* x8 = (int*)malloc(256 * sizeof(int));
  int x9 = 256 / x4;
  int x10 = x3 * x5;
  int x11 = x3 == x4 - 1 ? 79 : x10 + x5;
  int x12 = x10;
  while (x12 != x11) {
    int x13 = (int)"My name is Ozymandias, King of Kings; Look on my Works, ye Mighty, and despair!"[x12];
    int x14 = x13 % x4 * x9 + x13 / x4;
    x6[x14] = x6[x14] + 1;
    x12 = x12 + 1;
  }
  int x15 = 0;
  while (x15 != x4) {
    int x16 = x15;
    if (x16 != x3) MPI_Issend(x6 + (x16 * x9), x9, MPI_INT, x16, 0, MPI_COMM_WORLD, &req);
    x15 = x15 + 1;
  }
  int x17 = 0;
  while (x17 != x4) {
    int x18 = x17;
    if (x18 != x3) MPI_Irecv(x8 + (x18 * x9), x9, MPI_INT, x18, 0, MPI_COMM_WORLD, &req);
    x17 = x17 + 1;
  }
  MPI_Barrier(MPI_COMM_WORLD);
  int x19 = x3 * x9;
  int x20 = 0;
  while (x20 != x4) {
    int x21 = x20;
    if (x21 != x3) {
      int x22 = x21 * x9;
      int x23 = 0;
      while (x23 != x9) {
        int x24 = x23;
        int x25 = x19 + x24;
        x6[x25] = x6[x25] + x8[x22 + x24];
        x23 = x23 + 1;
      }
    }
    x20 = x20 + 1;
  }
  int x26 = 0;
  while (x26 != x9) {
    int x27 = x26;
    int x28 = x6[x19 + x27];
    if (x28 != 0) printf("%d: '%c' %d\n", x3, (char)(x3 + x27 * x4), x28);
    x26 = x26 + 1;
  }
  MPI_Finalize();
}
/*****************************************
End of C Generated Code
*******************************************/
int main(int argc, char *argv[]) {
  if (argc != 2) {
    printf("usage: %s <arg>\n", argv[0]);
    return 0;
  }
  Snippet(argv[1]);
  return 0;
}
