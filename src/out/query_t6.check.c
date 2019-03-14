#include <fcntl.h>
#include <errno.h>
#include <err.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdint.h>
#include <unistd.h>
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
long hash(char *str0, int len)
{
  unsigned char* str = (unsigned char*)str0;
  unsigned long hash = 5381;
  int c;
  while ((c = *str++) && len--)
  hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
  return hash;
}
void Snippet(char*);
int main(int argc, char *argv[])
{
  if (argc != 2) {
    printf("usage: query <filename>\n");
    return 0;
  }
  Snippet(argv[1]);
  return 0;
}
/*****************************************
Emitting C Generated Code
*******************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
void Snippet(char* x0) {
  printf("%s\n", "Name,Value");
  char** x2 = (char**)malloc(256 * sizeof(char*));
  int* x3 = (int*)malloc(256 * sizeof(int));
  int x4 = 0;
  int x5 = 0;
  int* x6 = (int*)malloc(256 * sizeof(int));
  int x7 = 0;
  while (x7 != 256) ({
    x6[x7] = -1;
    x7 = x7 + 1;
  });
  int* x9 = (int*)malloc(256 * sizeof(int));
  int x10 = 0;
  int x11 = open("src/data/t.csv",0);
  int x12 = fsize(x11);
  char* x13 = mmap(0, x12, PROT_READ, MAP_FILE | MAP_SHARED, x11, 0);
  int x14 = 0;
  while (x13[x14] != ',') ({
    x14 = x14 + 1;
  });
  x14 = x14 + 1;
  int x18 = 0;
  while (x13[x14] != ',') ({
    x18 = x18 * 10 + (int)(x13[x14] - '0');
    x14 = x14 + 1;
  });
  x14 = x14 + 1;
  while (x13[x14] != '\n') ({
    x14 = x14 + 1;
  });
  x14 = x14 + 1;
  long x24 = 0L * 41L;
  while (x14 < x12) ({
    int x25 = x14;
    while (x13[x14] != ',') ({
      x14 = x14 + 1;
    });
    int x27 = x14 - x25;
    x14 = x14 + 1;
    char* x28 = x13 + x25;
    int x29 = 0;
    while (x13[x14] != ',') ({
      x29 = x29 * 10 + (int)(x13[x14] - '0');
      x14 = x14 + 1;
    });
    x14 = x14 + 1;
    while (x13[x14] != '\n') ({
      x14 = x14 + 1;
    });
    x14 = x14 + 1;
    int x34 = ((int)(x24 + hash(x28, x27))) & 255;
    int x43 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x35 = x34;
      while (({
        x6[x35] != -1 && ({
          int x36 = x6[x35];
          char* x37 = x2[x36];
          int x38 = x3[x36];
          !(x38 == x27 && ({
            int x39 = 0;
            while (x39 < x38 && x37[x39] == x28[x39]) ({
              x39 = x39 + 1;
            });
            x39 == x38;
          }));
        });
      })) ({
        x35 = (x35 + 1) & 255;
      });
      ((x6[x35] == -1) ? ({
        int x42 = x5;
        x2[x42] = x28;
        x3[x42] = x27;
        x5 = x5 + 1;
        x6[x35] = x42;
        x9[x42] = 0;
        x42;
      }) : x6[x35])//#hash_lookup
      ;});
    x9[x43] = x9[x43] + x29;
  });
  close(x11);
  int x46 = x5;
  int x47 = 0;
  while (x47 != x46) ({
    int x48 = x47;
    printll(x2[x48]);
    printf(",");
    printf("%d", x9[x48]);
    printf("%s\n", "");
    x47 = x47 + 1;
  });
}
/*****************************************
End of C Generated Code
*******************************************/
