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
    int* x3 = (int*)malloc(256 * sizeof(int));
    int x4 = 0;
    while (x4 != 256) {
      x3[x4] = 0;
      x4 = x4 + 1;
    }
    int x5 = 0;
    while (x5 != 79) {
      int x6 = "My name is Ozymandias, King of Kings; Look on my Works, ye Mighty, and despair!"[x5];
      x3[x6] = x3[x6] + 1;
      x5 = x5 + 1;
    }
    int x7 = 0;
    while (x7 != 256) {
      int x8 = x7;
      if (x3[x8] != 0) printf("'%c' %d\n", (char)x8, x3[x8]);
      x7 = x7 + 1;
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
