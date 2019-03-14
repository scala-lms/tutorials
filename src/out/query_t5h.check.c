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
  printf("%s\n", "Name,Value,Flag,Name");
  char** x1 = (char**)malloc(256 * sizeof(char*));;
  int* x2 = (int*)malloc(256 * sizeof(int));;
  int x3 = 0;
  int x4 = 0;
  int* x5 = (int*)malloc(256 * sizeof(int));;
  int x6 = 0;
  while (x6 != 256) ({
    x5[x6] = -1;
    x6 = x6 + 1;
  });
  char** x7 = (char**)malloc(65536 * sizeof(char*));;
  int* x8 = (int*)malloc(65536 * sizeof(int));;
  int* x9 = (int*)malloc(65536 * sizeof(int));;
  char** x10 = (char**)malloc(65536 * sizeof(char*));;
  int* x11 = (int*)malloc(65536 * sizeof(int));;
  int x12 = 0;
  int x13 = 0;
  int* x14 = (int*)malloc(65536 * sizeof(int));;
  int* x15 = (int*)malloc(256 * sizeof(int));;
  int x16 = open("src/data/t.csv",0);
  int x17 = fsize(x16);
  char* x18 = mmap(0, x17, PROT_READ, MAP_FILE | MAP_SHARED, x16, 0);
  int x19 = 0;
  while (x18[x19] != ',') ({
    x19 = x19 + 1;
  });
  x19 = x19 + 1;
  int x20 = 0;
  while (x18[x19] != ',') ({
    x20 = x20 * 10 + (int)(x18[x19] - '0');
    x19 = x19 + 1;
  });
  x19 = x19 + 1;
  while (x18[x19] != '\n') ({
    x19 = x19 + 1;
  });
  x19 = x19 + 1;
  long x21 = 0L * 41L;
  while (x19 < x17) ({
    int x22 = x19;
    while (x18[x19] != ',') ({
      x19 = x19 + 1;
    });
    int x23 = x19 - x22;
    x19 = x19 + 1;
    char* x24 = x18 + x22;
    int x25 = 0;
    while (x18[x19] != ',') ({
      x25 = x25 * 10 + (int)(x18[x19] - '0');
      x19 = x19 + 1;
    });
    x19 = x19 + 1;
    int x26 = x19;
    while (x18[x19] != '\n') ({
      x19 = x19 + 1;
    });
    int x27 = x19;
    x19 = x19 + 1;
    int x28 = x13;
    x7[x28] = x24;
    x8[x28] = x23;
    x9[x28] = x25;
    x10[x28] = x18 + x26;
    x11[x28] = x27 - x26;
    x13 = x13 + 1;
    int x29 = ((int)(x21 + hash(x24, x23))) & 255;
    int x36 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x30 = x29;
      while (({
        x5[x30] != -1 && ({
          int x31 = x5[x30];
          char* x32 = x1[x31];
          int x33 = x2[x31];
          !(x33 == x23 && ({
            int x34 = 0;
            while (x34 < x33 && x32[x34] == x24[x34]) ({
              x34 = x34 + 1;
            });
            x34 == x33;
          }));
        });
      })) ({
        x30 = (x30 + 1) & 255;
      });
      ((x5[x30] == -1) ? ({
        int x35 = x4;
        x1[x35] = x24;
        x2[x35] = x23;
        x4 = x4 + 1;
        x5[x30] = x35;
        x15[x35] = 0;
        x35;
      }) : x5[x30])//#hash_lookup
      ;});
    int x37 = x15[x36];
    x14[x36 * 256 + x37] = x28;
    x15[x36] = x37 + 1;
  });
  close(x16);
  int x38 = 0;
  while (x18[x38] != ',') ({
    x38 = x38 + 1;
  });
  x38 = x38 + 1;
  int x39 = 0;
  while (x18[x38] != ',') ({
    x39 = x39 * 10 + (int)(x18[x38] - '0');
    x38 = x38 + 1;
  });
  x38 = x38 + 1;
  while (x18[x38] != '\n') ({
    x38 = x38 + 1;
  });
  x38 = x38 + 1;
  while (x38 < x17) ({
    int x40 = x38;
    while (x18[x38] != ',') ({
      x38 = x38 + 1;
    });
    int x41 = x38 - x40;
    x38 = x38 + 1;
    char* x42 = x18 + x40;
    int x43 = 0;
    while (x18[x38] != ',') ({
      x43 = x43 * 10 + (int)(x18[x38] - '0');
      x38 = x38 + 1;
    });
    x38 = x38 + 1;
    while (x18[x38] != '\n') ({
      x38 = x38 + 1;
    });
    x38 = x38 + 1;
    int x44 = ((int)(x21 + hash(x42, x41))) & 255;
    int x50 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x45 = x44;
      while (({
        x5[x45] != -1 && ({
          int x46 = x5[x45];
          char* x47 = x1[x46];
          int x48 = x2[x46];
          !(x48 == x41 && ({
            int x49 = 0;
            while (x49 < x48 && x47[x49] == x42[x49]) ({
              x49 = x49 + 1;
            });
            x49 == x48;
          }));
        });
      })) ({
        x45 = (x45 + 1) & 255;
      });
      x5[x45]//#hash_lookup
      ;});
    ((x50 != -1) ? ({
      int x51 = x50 * 256;
      int x52 = x51 + x15[x50];
      int x53 = x51;
      while (x53 != x52) ({
        int x54 = x14[x53];
        printll(x7[x54]);
        printf(",");
        printf("%d", x9[x54]);
        printf(",");
        printll(x10[x54]);
        printf(",");
        printll(x42);
        printf("%s\n", "");
        x53 = x53 + 1;
      });
    }) : ({}));
  });
  close(x16);
}
/*****************************************
End of C Generated Code
*******************************************/
