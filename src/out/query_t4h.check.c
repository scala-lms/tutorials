#include <fcntl.h>
#include <errno.h>
#include <err.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdint.h>
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
  printf("%s\n","Name,Value,Flag,Name1");
  int32_t x2 = 0;
  int32_t x3 = 0;
  int32_t* x4 = (int32_t*)malloc(256 * sizeof(int32_t));
  for(int x6=0; x6 < 256; x6++) {
    x4[x6] = -1;
  }
  char** x10 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x11 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t* x12 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x13 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x14 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t x15 = 0;
  int32_t x16 = 0;
  int32_t* x17 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t* x18 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x22 = 0;
  int32_t x19 = open("src/data/t.csv",0);
  int32_t x20 = fsize(x19);
  char* x21 = mmap(0, x20, PROT_READ, MAP_FILE | MAP_SHARED, x19, 0);
  for (;;) {
    int32_t x23 = x22;
    char x24 = x21[x23];
    bool x25 = x24 != ',';
    if (!x25) break;
    x22 += 1;
  }
  int32_t x30 = x22;
  x22 += 1;
  int32_t x33 = x22;
  int32_t x34 = 0;
  for (;;) {
    int32_t x35 = x22;
    char x36 = x21[x35];
    bool x37 = x36 != ',';
    if (!x37) break;
    int32_t x39 = x34;
    int32_t x41 = x22;
    int32_t x40 = x39 * 10;
    char x42 = x21[x41];
    char x43 = x42 - '0';
    int32_t x44 = x40 + x43;
    x34 = x44;
    x22 += 1;
  }
  x22 += 1;
  int32_t x50 = x34;
  int32_t x51 = x22;
  for (;;) {
    int32_t x52 = x22;
    char x53 = x21[x52];
    bool x54 = x53 != '\n';
    if (!x54) break;
    x22 += 1;
  }
  int32_t x59 = x22;
  x22 += 1;
  int32_t x115 = (int32_t)0L;
  int32_t x116 = x115 & 255;
  bool x121 = !true;
  for (;;) {
    int32_t x63 = x22;
    bool x64 = x63 < x20;
    if (!x64) break;
    int32_t x66 = x22;
    for (;;) {
      int32_t x67 = x22;
      char x68 = x21[x67];
      bool x69 = x68 != ',';
      if (!x69) break;
      x22 += 1;
    }
    int32_t x74 = x22;
    x22 += 1;
    int32_t x78 = x22;
    int32_t x79 = 0;
    for (;;) {
      int32_t x80 = x22;
      char x81 = x21[x80];
      bool x82 = x81 != ',';
      if (!x82) break;
      int32_t x84 = x79;
      int32_t x86 = x22;
      int32_t x85 = x84 * 10;
      char x87 = x21[x86];
      char x88 = x87 - '0';
      int32_t x89 = x85 + x88;
      x79 = x89;
      x22 += 1;
    }
    x22 += 1;
    int32_t x95 = x79;
    int32_t x96 = x22;
    for (;;) {
      int32_t x97 = x22;
      char x98 = x21[x97];
      bool x99 = x98 != '\n';
      if (!x99) break;
      x22 += 1;
    }
    int32_t x104 = x22;
    x22 += 1;
    int32_t x108 = x16;
    char* x77 = x21+x66;
    x10[x108] = x77;
    int32_t x75 = x74 - x66;
    x11[x108] = x75;
    x12[x108] = x95;
    char* x107 = x21+x96;
    x13[x108] = x107;
    int32_t x105 = x104 - x96;
    x14[x108] = x105;
    x16 += 1;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x117 = x116;
    for (;;) {
      int32_t x118 = x117;
      int32_t x119 = x4[x118];
      bool x120 = x119 != -1;
      bool x122 = x120 && x121;
      if (!x122) break;
      int32_t x124 = x117;
      int32_t x125 = x124 + 1;
      int32_t x126 = x125 & 255;
      x117 = x126;
    }
    int32_t x130 = x117;
    int32_t x131 = x4[x130];
    bool x132 = x131 == -1;
    int32_t x138;
    if (x132) {
      int32_t x133 = x3;
      x3 += 1;
      x4[x130] = x133;
      x18[x133] = 0;
      x138 = x133;
    } else {
      x138 = x131;
    }
    int32_t x140 = x138;
    //#hash_lookup
    int32_t x141 = x18[x140];
    int32_t x142 = x140 * 256;
    int32_t x143 = x142 + x141;
    x17[x143] = x108;
    int32_t x145 = x141 + 1;
    x18[x140] = x145;
  }
  int32_t x149 = 0;
  for (;;) {
    int32_t x150 = x149;
    char x151 = x21[x150];
    bool x152 = x151 != ',';
    if (!x152) break;
    x149 += 1;
  }
  int32_t x157 = x149;
  x149 += 1;
  int32_t x159 = x149;
  int32_t x160 = 0;
  for (;;) {
    int32_t x161 = x149;
    char x162 = x21[x161];
    bool x163 = x162 != ',';
    if (!x163) break;
    int32_t x165 = x160;
    int32_t x167 = x149;
    int32_t x166 = x165 * 10;
    char x168 = x21[x167];
    char x169 = x168 - '0';
    int32_t x170 = x166 + x169;
    x160 = x170;
    x149 += 1;
  }
  x149 += 1;
  int32_t x176 = x160;
  int32_t x177 = x149;
  for (;;) {
    int32_t x178 = x149;
    char x179 = x21[x178];
    bool x180 = x179 != '\n';
    if (!x180) break;
    x149 += 1;
  }
  int32_t x185 = x149;
  x149 += 1;
  for (;;) {
    int32_t x189 = x149;
    bool x190 = x189 < x20;
    if (!x190) break;
    int32_t x192 = x149;
    for (;;) {
      int32_t x193 = x149;
      char x194 = x21[x193];
      bool x195 = x194 != ',';
      if (!x195) break;
      x149 += 1;
    }
    int32_t x200 = x149;
    x149 += 1;
    int32_t x204 = x149;
    int32_t x205 = 0;
    for (;;) {
      int32_t x206 = x149;
      char x207 = x21[x206];
      bool x208 = x207 != ',';
      if (!x208) break;
      int32_t x210 = x205;
      int32_t x212 = x149;
      int32_t x211 = x210 * 10;
      char x213 = x21[x212];
      char x214 = x213 - '0';
      int32_t x215 = x211 + x214;
      x205 = x215;
      x149 += 1;
    }
    x149 += 1;
    int32_t x221 = x205;
    int32_t x222 = x149;
    for (;;) {
      int32_t x223 = x149;
      char x224 = x21[x223];
      bool x225 = x224 != '\n';
      if (!x225) break;
      x149 += 1;
    }
    int32_t x230 = x149;
    x149 += 1;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x234 = x116;
    for (;;) {
      int32_t x235 = x234;
      int32_t x236 = x4[x235];
      bool x237 = x236 != -1;
      bool x238 = x237 && x121;
      if (!x238) break;
      int32_t x240 = x234;
      int32_t x241 = x240 + 1;
      int32_t x242 = x241 & 255;
      x234 = x242;
    }
    int32_t x246 = x234;
    int32_t x247 = x4[x246];
    int32_t x249 = x247;
    //#hash_lookup
    int32_t x250 = x18[x249];
    int32_t x251 = x249 * 256;
    int32_t x252 = x251 + x250;
    char* x203 = x21+x192;
    for(int x254=x251; x254 < x252; x254++) {
      int32_t x255 = x17[x254];
      char* x256 = x10[x255];
      int32_t x257 = x11[x255];
      int32_t x258 = x12[x255];
      char* x259 = x13[x255];
      int32_t x260 = x14[x255];
      int32_t x261 = printll(x256);
      printf(",");
      printf("%d",x258);
      printf(",");
      int32_t x265 = printll(x259);
      printf(",");
      int32_t x267 = printll(x203);
      printf("%s\n","");
    }
  }
}
/*****************************************
End of C Generated Code
*******************************************/
