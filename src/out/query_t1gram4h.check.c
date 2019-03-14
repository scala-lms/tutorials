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
  printf("%s\n", "Word,Value,Word,Year,MatchCount,VolumeCount");
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
  char** x9 = (char**)malloc(65536 * sizeof(char*));
  int* x10 = (int*)malloc(65536 * sizeof(int));
  int* x11 = (int*)malloc(65536 * sizeof(int));
  int x12 = 0;
  int x13 = 0;
  int* x14 = (int*)malloc(65536 * sizeof(int));
  int* x15 = (int*)malloc(256 * sizeof(int));
  int x16 = open("src/data/words.csv",0);
  int x17 = fsize(x16);
  char* x18 = mmap(0, x17, PROT_READ, MAP_FILE | MAP_SHARED, x16, 0);
  int x19 = 0;
  while (x18[x19] != ',') ({
    x19 = x19 + 1;
  });
  x19 = x19 + 1;
  int x23 = 0;
  while (x18[x19] != '\n') ({
    x23 = x23 * 10 + (int)(x18[x19] - '0');
    x19 = x19 + 1;
  });
  x19 = x19 + 1;
  long x26 = 0L * 41L;
  while (x19 < x17) ({
    int x27 = x19;
    while (x18[x19] != ',') ({
      x19 = x19 + 1;
    });
    int x29 = x19 - x27;
    x19 = x19 + 1;
    char* x30 = x18 + x27;
    int x31 = 0;
    while (x18[x19] != '\n') ({
      x31 = x31 * 10 + (int)(x18[x19] - '0');
      x19 = x19 + 1;
    });
    x19 = x19 + 1;
    int x33 = x13;
    x9[x33] = x30;
    x10[x33] = x29;
    x11[x33] = x31;
    x13 = x13 + 1;
    int x34 = ((int)(x26 + hash(x30, x29))) & 255;
    int x43 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x35 = x34;
      while (({
        x6[x35] != -1 && ({
          int x36 = x6[x35];
          char* x37 = x2[x36];
          int x38 = x3[x36];
          !(x38 == x29 && ({
            int x39 = 0;
            while (x39 < x38 && x37[x39] == x30[x39]) ({
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
        x2[x42] = x30;
        x3[x42] = x29;
        x5 = x5 + 1;
        x6[x35] = x42;
        x15[x42] = 0;
        x42;
      }) : x6[x35])//#hash_lookup
      ;});
    int x44 = x15[x43];
    x14[x43 * 256 + x44] = x33;
    x15[x43] = x44 + 1;
  });
  close(x16);
  int x47 = open(x0,0);
  int x48 = fsize(x47);
  char* x49 = mmap(0, x48, PROT_READ, MAP_FILE | MAP_SHARED, x47, 0);
  int x50 = 0;
  while (x50 < x48) ({
    int x51 = x50;
    while (x49[x50] != '\t') ({
      x50 = x50 + 1;
    });
    int x53 = x50 - x51;
    x50 = x50 + 1;
    char* x54 = x49 + x51;
    int x55 = x50;
    while (x49[x50] != '\t') ({
      x50 = x50 + 1;
    });
    x50 = x50 + 1;
    int x58 = x50;
    while (x49[x50] != '\t') ({
      x50 = x50 + 1;
    });
    x50 = x50 + 1;
    int x61 = x50;
    while (x49[x50] != '\n') ({
      x50 = x50 + 1;
    });
    x50 = x50 + 1;
    int x64 = ((int)(x26 + hash(x54, x53))) & 255;
    int x72 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x65 = x64;
      while (({
        x6[x65] != -1 && ({
          int x66 = x6[x65];
          char* x67 = x2[x66];
          int x68 = x3[x66];
          !(x68 == x53 && ({
            int x69 = 0;
            while (x69 < x68 && x67[x69] == x54[x69]) ({
              x69 = x69 + 1;
            });
            x69 == x68;
          }));
        });
      })) ({
        x65 = (x65 + 1) & 255;
      });
      x6[x65]//#hash_lookup
      ;});
    ((x72 != -1) ? ({
      char* x73 = x49 + x55;
      char* x74 = x49 + x58;
      char* x75 = x49 + x61;
      int x76 = x72 * 256;
      int x77 = x76 + x15[x72];
      int x78 = x76;
      while (x78 != x77) ({
        int x79 = x14[x78];
        printll(x9[x79]);
        printf(",");
        printf("%d", x11[x79]);
        printf(",");
        printll(x54);
        printf(",");
        printll(x73);
        printf(",");
        printll(x74);
        printf(",");
        printll(x75);
        printf("%s\n", "");
        x78 = x78 + 1;
      });
    }) : ({}));
  });
  close(x47);
}
/*****************************************
End of C Generated Code
*******************************************/
