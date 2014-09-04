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
  bool x122 = !true;
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
      bool x121 = x119 == -1;
      bool x123;
      if (x121) {
        x123 = false;
      } else {
        x123 = x122;
      }
      if (!x123) break;
      int32_t x125 = x117;
      int32_t x126 = x125 + 1;
      int32_t x127 = x126 & 255;
      x117 = x127;
    }
    int32_t x131 = x117;
    int32_t x132 = x4[x131];
    bool x133 = x132 == -1;
    int32_t x139;
    if (x133) {
      int32_t x134 = x3;
      x3 += 1;
      x4[x131] = x134;
      x18[x134] = 0;
      x139 = x134;
    } else {
      x139 = x132;
    }
    int32_t x141 = x139;
    //#hash_lookup
    int32_t x142 = x18[x141];
    int32_t x143 = x141 * 256;
    int32_t x144 = x143 + x142;
    x17[x144] = x108;
    int32_t x146 = x142 + 1;
    x18[x141] = x146;
  }
  int32_t x150 = 0;
  for (;;) {
    int32_t x151 = x150;
    char x152 = x21[x151];
    bool x153 = x152 != ',';
    if (!x153) break;
    x150 += 1;
  }
  int32_t x158 = x150;
  x150 += 1;
  int32_t x160 = x150;
  int32_t x161 = 0;
  for (;;) {
    int32_t x162 = x150;
    char x163 = x21[x162];
    bool x164 = x163 != ',';
    if (!x164) break;
    int32_t x166 = x161;
    int32_t x168 = x150;
    int32_t x167 = x166 * 10;
    char x169 = x21[x168];
    char x170 = x169 - '0';
    int32_t x171 = x167 + x170;
    x161 = x171;
    x150 += 1;
  }
  x150 += 1;
  int32_t x177 = x161;
  int32_t x178 = x150;
  for (;;) {
    int32_t x179 = x150;
    char x180 = x21[x179];
    bool x181 = x180 != '\n';
    if (!x181) break;
    x150 += 1;
  }
  int32_t x186 = x150;
  x150 += 1;
  for (;;) {
    int32_t x190 = x150;
    bool x191 = x190 < x20;
    if (!x191) break;
    int32_t x193 = x150;
    for (;;) {
      int32_t x194 = x150;
      char x195 = x21[x194];
      bool x196 = x195 != ',';
      if (!x196) break;
      x150 += 1;
    }
    int32_t x201 = x150;
    x150 += 1;
    int32_t x205 = x150;
    int32_t x206 = 0;
    for (;;) {
      int32_t x207 = x150;
      char x208 = x21[x207];
      bool x209 = x208 != ',';
      if (!x209) break;
      int32_t x211 = x206;
      int32_t x213 = x150;
      int32_t x212 = x211 * 10;
      char x214 = x21[x213];
      char x215 = x214 - '0';
      int32_t x216 = x212 + x215;
      x206 = x216;
      x150 += 1;
    }
    x150 += 1;
    int32_t x222 = x206;
    int32_t x223 = x150;
    for (;;) {
      int32_t x224 = x150;
      char x225 = x21[x224];
      bool x226 = x225 != '\n';
      if (!x226) break;
      x150 += 1;
    }
    int32_t x231 = x150;
    x150 += 1;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x235 = x116;
    for (;;) {
      int32_t x236 = x235;
      int32_t x237 = x4[x236];
      bool x239 = x237 == -1;
      bool x240;
      if (x239) {
        x240 = false;
      } else {
        x240 = x122;
      }
      if (!x240) break;
      int32_t x242 = x235;
      int32_t x243 = x242 + 1;
      int32_t x244 = x243 & 255;
      x235 = x244;
    }
    int32_t x248 = x235;
    int32_t x249 = x4[x248];
    int32_t x251 = x249;
    //#hash_lookup
    int32_t x252 = x18[x251];
    int32_t x253 = x251 * 256;
    int32_t x254 = x253 + x252;
    char* x204 = x21+x193;
    for(int x256=x253; x256 < x254; x256++) {
      int32_t x257 = x17[x256];
      char* x258 = x10[x257];
      int32_t x259 = x11[x257];
      int32_t x260 = x12[x257];
      char* x261 = x13[x257];
      int32_t x262 = x14[x257];
      int32_t x263 = printll(x258);
      printf(",");
      printf("%d",x260);
      printf(",");
      int32_t x267 = printll(x261);
      printf(",");
      int32_t x269 = printll(x204);
      printf("%s\n","");
    }
  }
}
/*****************************************
End of C Generated Code
*******************************************/
