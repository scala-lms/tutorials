#include <fcntl.h>
#include <errno.h>
#include <err.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <stdio.h>
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
unsigned long hash(unsigned char *str) // FIXME: need to take length!
{
  unsigned long hash = 5381;
  int c;
  while ((c = *str++))
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
void Snippet(char*  x0) {
  printf("%s\n","Name,Value");
  char** x2 = (char**)malloc(256 * sizeof(char*));
  int32_t* x3 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x4 = 0;
  int32_t x5 = 0;
  int32_t* x6 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x7 = 0;
  int32_t* x8 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t* x9 = (int32_t*)malloc(256 * sizeof(int32_t));
  for(int x11=0; x11 < 256; x11++) {
    x9[x11] = -1;
  }
  int32_t x18 = 0;
  int32_t x15 = open("src/data/t.csv",0);
  int32_t x16 = fsize(x15);
  char* x17 = mmap(0, x16, PROT_READ, MAP_FILE | MAP_SHARED, x15, 0);
  for (;;) {
    int32_t x19 = x18;
    char x20 = x17[x19];
    bool x21 = x20 != ',';
    if (!x21) break;
    x18 += 1;
  }
  int32_t x26 = x18;
  x18 += 1;
  int32_t x29 = x18;
  int32_t x30 = 0;
  for (;;) {
    int32_t x31 = x18;
    char x32 = x17[x31];
    bool x33 = x32 != ',';
    if (!x33) break;
    int32_t x35 = x30;
    int32_t x37 = x18;
    int32_t x36 = x35 * 10;
    char x38 = x17[x37];
    char x39 = x38 - '0';
    int32_t x40 = x36 + x39;
    x30 = x40;
    x18 += 1;
  }
  x18 += 1;
  int32_t x46 = x30;
  int32_t x47 = x18;
  for (;;) {
    int32_t x48 = x18;
    char x49 = x17[x48];
    bool x50 = x49 != '\n';
    if (!x50) break;
    x18 += 1;
  }
  int32_t x55 = x18;
  x18 += 1;
  for (;;) {
    int32_t x59 = x18;
    bool x60 = x59 < x16;
    if (!x60) break;
    int32_t x62 = x18;
    for (;;) {
      int32_t x63 = x18;
      char x64 = x17[x63];
      bool x65 = x64 != ',';
      if (!x65) break;
      x18 += 1;
    }
    int32_t x70 = x18;
    x18 += 1;
    int32_t x74 = x18;
    int32_t x75 = 0;
    for (;;) {
      int32_t x76 = x18;
      char x77 = x17[x76];
      bool x78 = x77 != ',';
      if (!x78) break;
      int32_t x80 = x75;
      int32_t x82 = x18;
      int32_t x81 = x80 * 10;
      char x83 = x17[x82];
      char x84 = x83 - '0';
      int32_t x85 = x81 + x84;
      x75 = x85;
      x18 += 1;
    }
    x18 += 1;
    int32_t x91 = x75;
    int32_t x92 = x18;
    for (;;) {
      int32_t x93 = x18;
      char x94 = x17[x93];
      bool x95 = x94 != '\n';
      if (!x95) break;
      x18 += 1;
    }
    int32_t x100 = x18;
    x18 += 1;
    char* x73 = x17+x62;
    char x104 = x73[0];
    int32_t x105 = (int32_t)x104;
    int32_t x106 = x105 & 255;
    int32_t x71 = x70 - x62;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x107 = x106;
    for (;;) {
      int32_t x108 = x107;
      int32_t x109 = x9[x108];
      char* x111 = x2[x109];
      int32_t x112 = x3[x109];
      bool x110 = x109 != -1;
      bool x114 = x112 == x71;
      bool x129;
      if (x114) {
        int32_t x115 = 0;
        for (;;) {
          int32_t x116 = x115;
          bool x117 = x116 < x112;
          char x118 = x111[x116];
          char x119 = x73[x116];
          bool x120 = x118 == x119;
          bool x121 = x117 && x120;
          if (!x121) break;
          x115 += 1;
        }
        int32_t x126 = x115;
        bool x127 = x126 == x112;
        x129 = x127;
      } else {
        x129 = false;
      }
      bool x130 = !x129;
      bool x131 = x110 && x130;
      if (!x131) break;
      int32_t x133 = x107;
      int32_t x134 = x133 + 1;
      int32_t x135 = x134 & 255;
      x107 = x135;
    }
    int32_t x139 = x107;
    int32_t x140 = x9[x139];
    bool x141 = x140 == -1;
    bool x142 = true && x141;
    int32_t x150;
    if (x142) {
      int32_t x143 = x5;
      x9[x139] = x143;
      x2[x143] = x73;
      x3[x143] = x71;
      x6[x143] = 0;
      x5 += 1;
      x150 = x143;
    } else {
      x150 = x140;
    }
    int32_t x152 = x150;
    //#hash_lookup
    int32_t x153 = x6[x152];
    int32_t x154 = x153 + x91;
    x6[x152] = x154;
  }
  int32_t x158 = x5;
  for(int x160=0; x160 < x158; x160++) {
    char* x161 = x2[x160];
    int32_t x162 = x3[x160];
    int32_t x163 = x6[x160];
    int32_t x164 = printll(x161);
    printf(",");
    printf("%d",x163);
    printf("%s\n","");
  }
}
/*****************************************
End of C Generated Code
*******************************************/
