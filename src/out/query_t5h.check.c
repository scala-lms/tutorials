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
  char** x12 = (char**)malloc(65536 * sizeof(char*));
  int* x13 = (int*)malloc(65536 * sizeof(int));
  int x14 = 0;
  int x15 = 0;
  int* x16 = (int*)malloc(65536 * sizeof(int));
  int* x17 = (int*)malloc(256 * sizeof(int));
  int x18 = open("src/data/t.csv",0);
  int x19 = fsize(x18);
  char* x20 = mmap(0, x19, PROT_READ, MAP_FILE | MAP_SHARED, x18, 0);
  int x21 = 0;
  while (x20[x21] != ',') ({
    x21 = x21 + 1;
  });
  x21 = x21 + 1;
  int x25 = 0;
  while (x20[x21] != ',') ({
    x25 = x25 * 10 + (int)(x20[x21] - '0');
    x21 = x21 + 1;
  });
  x21 = x21 + 1;
  while (x20[x21] != '\n') ({
    x21 = x21 + 1;
  });
  x21 = x21 + 1;
  long x31 = 0L * 41L;
  while (x21 < x19) ({
    int x32 = x21;
    while (x20[x21] != ',') ({
      x21 = x21 + 1;
    });
    int x34 = x21 - x32;
    x21 = x21 + 1;
    char* x35 = x20 + x32;
    int x36 = 0;
    while (x20[x21] != ',') ({
      x36 = x36 * 10 + (int)(x20[x21] - '0');
      x21 = x21 + 1;
    });
    x21 = x21 + 1;
    int x38 = x21;
    while (x20[x21] != '\n') ({
      x21 = x21 + 1;
    });
    int x40 = x21;
    x21 = x21 + 1;
    int x41 = x15;
    x9[x41] = x35;
    x10[x41] = x34;
    x11[x41] = x36;
    x12[x41] = x20 + x38;
    x13[x41] = x40 - x38;
    x15 = x15 + 1;
    int x42 = ((int)(x31 + hash(x35, x34))) & 255;
    int x51 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x43 = x42;
      while (({
        x6[x43] != -1 && ({
          int x44 = x6[x43];
          char* x45 = x2[x44];
          int x46 = x3[x44];
          !(x46 == x34 && ({
            int x47 = 0;
            while (x47 < x46 && x45[x47] == x35[x47]) ({
              x47 = x47 + 1;
            });
            x47 == x46;
          }));
        });
      })) ({
        x43 = (x43 + 1) & 255;
      });
      ((x6[x43] == -1) ? ({
        int x50 = x5;
        x2[x50] = x35;
        x3[x50] = x34;
        x5 = x5 + 1;
        x6[x43] = x50;
        x17[x50] = 0;
        x50;
      }) : x6[x43])//#hash_lookup
      ;});
    int x52 = x17[x51];
    x16[x51 * 256 + x52] = x41;
    x17[x51] = x52 + 1;
  });
  close(x18);
  int x55 = 0;
  while (x20[x55] != ',') ({
    x55 = x55 + 1;
  });
  x55 = x55 + 1;
  int x59 = 0;
  while (x20[x55] != ',') ({
    x59 = x59 * 10 + (int)(x20[x55] - '0');
    x55 = x55 + 1;
  });
  x55 = x55 + 1;
  while (x20[x55] != '\n') ({
    x55 = x55 + 1;
  });
  x55 = x55 + 1;
  while (x55 < x19) ({
    int x65 = x55;
    while (x20[x55] != ',') ({
      x55 = x55 + 1;
    });
    int x67 = x55 - x65;
    x55 = x55 + 1;
    char* x68 = x20 + x65;
    int x69 = 0;
    while (x20[x55] != ',') ({
      x69 = x69 * 10 + (int)(x20[x55] - '0');
      x55 = x55 + 1;
    });
    x55 = x55 + 1;
    while (x20[x55] != '\n') ({
      x55 = x55 + 1;
    });
    x55 = x55 + 1;
    int x75 = ((int)(x31 + hash(x68, x67))) & 255;
    int x83 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x76 = x75;
      while (({
        x6[x76] != -1 && ({
          int x77 = x6[x76];
          char* x78 = x2[x77];
          int x79 = x3[x77];
          !(x79 == x67 && ({
            int x80 = 0;
            while (x80 < x79 && x78[x80] == x68[x80]) ({
              x80 = x80 + 1;
            });
            x80 == x79;
          }));
        });
      })) ({
        x76 = (x76 + 1) & 255;
      });
      x6[x76]//#hash_lookup
      ;});
    ((x83 != -1) ? ({
      int x84 = x83 * 256;
      int x85 = x84 + x17[x83];
      int x86 = x84;
      while (x86 != x85) ({
        int x87 = x16[x86];
        printll(x9[x87]);
        printf(",");
        printf("%d", x11[x87]);
        printf(",");
        printll(x12[x87]);
        printf(",");
        printll(x68);
        printf("%s\n", "");
        x86 = x86 + 1;
      });
    }) : ({}));
  });
  close(x18);
}
/*****************************************
End of C Generated Code
*******************************************/
