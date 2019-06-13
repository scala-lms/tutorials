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
  if (x2 == 0) {
    char** x3 = (char**)malloc(256 * sizeof(char*));
    int* x4 = (int*)malloc(256 * sizeof(int));
    int x5 = 0;
    int* x6 = (int*)malloc(256 * sizeof(int));
    int x7 = 0;
    while (x7 != 256) {
      x6[x7] = -1;
      x7 = x7 + 1;
    }
    int* x8 = (int*)malloc(256 * sizeof(int));
    char* x9 = "foo bar baz foo bar foo foo foo boom bang boom boom yum";
    int x10 = 0;
    while (x10 < 55) {
      int x11 = x10;
      while (x9[x10] != ' ') x10 = x10 + 1;
      int x12 = x10 - x11;
      x10 = x10 + 1;
      char* x13 = x9 + x11;
      int x14 = (int)hash(x13, x12) & 255;
      //# hash_lookup
      // generated code for hash lookup
      int x15 = ({
        int x16 = x14;
        while (x6[x16] != -1 && ({
          int x17 = x6[x16];
          int x18 = x4[x17];
          !(x18 == x12 && ({
            char* x19 = x3[x17];
            int x20 = 0;
            while (x20 < x18 && x19[x20] == x13[x20]) x20 = x20 + 1;
            x20 == x18;
          }));
        })) x16 = x16 + 1 & 255;
        x6[x16] == -1 ? ({
          int x21 = x5;
          x3[x21] = x13;
          x4[x21] = x12;
          x5 = x5 + 1;
          x6[x16] = x21;
          x8[x21] = 0;
          x21;
        }) : x6[x16];
      });
      //# hash_lookup
      x8[x15] = x8[x15] + 1;
    }
    int x22 = x5;
    int x23 = 0;
    while (x23 != x22) {
      int x24 = x23;
      printf("%.*s", x4[x24], x3[x24]);
      printf(" ");
      printf("%d", x8[x24]);
      printf("\n");
      x23 = x23 + 1;
    }
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
