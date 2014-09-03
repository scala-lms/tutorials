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
  printf("%s\n","Name,Value,Flag,Name");
  char** x2 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x3 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t* x4 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x5 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x6 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t x7 = 0;
  int32_t x8 = 0;
  int32_t* x9 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x10 = (char**)malloc(256 * sizeof(char*));
  int32_t* x11 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x12 = 0;
  int32_t* x13 = (int32_t*)malloc(256 * sizeof(int32_t));
  for(int x15=0; x15 < 256; x15++) {
    x13[x15] = 0;
  }
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
    int32_t x108 = x8;
    char* x77 = x21+x66;
    x2[x108] = x77;
    int32_t x75 = x74 - x66;
    x3[x108] = x75;
    x4[x108] = x95;
    char* x107 = x21+x96;
    x5[x108] = x107;
    int32_t x105 = x104 - x96;
    x6[x108] = x105;
    x8 += 1;
    char x115 = x77[0];
    int32_t x116 = (int32_t)x115;
    int32_t x117 = x116 & 255;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x118 = x117;
    for (;;) {
      int32_t x119 = x118;
      int32_t x120 = x13[x119];
      char* x122 = x10[x119];
      int32_t x123 = x11[x119];
      bool x121 = x120 != 0;
      bool x125 = x123 == x75;
      bool x140;
      if (x125) {
        int32_t x126 = 0;
        for (;;) {
          int32_t x127 = x126;
          bool x128 = x127 < x123;
          char x129 = x122[x127];
          char x130 = x77[x127];
          bool x131 = x129 == x130;
          bool x132 = x128 && x131;
          if (!x132) break;
          x126 += 1;
        }
        int32_t x137 = x126;
        bool x138 = x137 == x123;
        x140 = x138;
      } else {
        x140 = false;
      }
      bool x141 = !x140;
      bool x142 = x121 && x141;
      if (!x142) break;
      int32_t x144 = x118;
      int32_t x145 = x144 + 1;
      int32_t x146 = x145 & 255;
      x118 = x146;
    }
    int32_t x150 = x118;
    x10[x150] = x77;
    x11[x150] = x75;
    int32_t x154 = x150;
    //#hash_lookup
    int32_t x155 = x13[x154];
    int32_t x156 = x154 * 256;
    int32_t x157 = x156 + x155;
    x9[x157] = x108;
    int32_t x159 = x155 + 1;
    x13[x154] = x159;
  }
  int32_t x163 = 0;
  for (;;) {
    int32_t x164 = x163;
    char x165 = x21[x164];
    bool x166 = x165 != ',';
    if (!x166) break;
    x163 += 1;
  }
  int32_t x171 = x163;
  x163 += 1;
  int32_t x173 = x163;
  int32_t x174 = 0;
  for (;;) {
    int32_t x175 = x163;
    char x176 = x21[x175];
    bool x177 = x176 != ',';
    if (!x177) break;
    int32_t x179 = x174;
    int32_t x181 = x163;
    int32_t x180 = x179 * 10;
    char x182 = x21[x181];
    char x183 = x182 - '0';
    int32_t x184 = x180 + x183;
    x174 = x184;
    x163 += 1;
  }
  x163 += 1;
  int32_t x190 = x174;
  int32_t x191 = x163;
  for (;;) {
    int32_t x192 = x163;
    char x193 = x21[x192];
    bool x194 = x193 != '\n';
    if (!x194) break;
    x163 += 1;
  }
  int32_t x199 = x163;
  x163 += 1;
  for (;;) {
    int32_t x203 = x163;
    bool x204 = x203 < x20;
    if (!x204) break;
    int32_t x206 = x163;
    for (;;) {
      int32_t x207 = x163;
      char x208 = x21[x207];
      bool x209 = x208 != ',';
      if (!x209) break;
      x163 += 1;
    }
    int32_t x214 = x163;
    x163 += 1;
    int32_t x218 = x163;
    int32_t x219 = 0;
    for (;;) {
      int32_t x220 = x163;
      char x221 = x21[x220];
      bool x222 = x221 != ',';
      if (!x222) break;
      int32_t x224 = x219;
      int32_t x226 = x163;
      int32_t x225 = x224 * 10;
      char x227 = x21[x226];
      char x228 = x227 - '0';
      int32_t x229 = x225 + x228;
      x219 = x229;
      x163 += 1;
    }
    x163 += 1;
    int32_t x235 = x219;
    int32_t x236 = x163;
    for (;;) {
      int32_t x237 = x163;
      char x238 = x21[x237];
      bool x239 = x238 != '\n';
      if (!x239) break;
      x163 += 1;
    }
    int32_t x244 = x163;
    x163 += 1;
    char* x217 = x21+x206;
    char x248 = x217[0];
    int32_t x249 = (int32_t)x248;
    int32_t x250 = x249 & 255;
    int32_t x215 = x214 - x206;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x251 = x250;
    for (;;) {
      int32_t x252 = x251;
      int32_t x253 = x13[x252];
      char* x255 = x10[x252];
      int32_t x256 = x11[x252];
      bool x254 = x253 != 0;
      bool x258 = x256 == x215;
      bool x273;
      if (x258) {
        int32_t x259 = 0;
        for (;;) {
          int32_t x260 = x259;
          bool x261 = x260 < x256;
          char x262 = x255[x260];
          char x263 = x217[x260];
          bool x264 = x262 == x263;
          bool x265 = x261 && x264;
          if (!x265) break;
          x259 += 1;
        }
        int32_t x270 = x259;
        bool x271 = x270 == x256;
        x273 = x271;
      } else {
        x273 = false;
      }
      bool x274 = !x273;
      bool x275 = x254 && x274;
      if (!x275) break;
      int32_t x277 = x251;
      int32_t x278 = x277 + 1;
      int32_t x279 = x278 & 255;
      x251 = x279;
    }
    int32_t x283 = x251;
    int32_t x285 = x283;
    //#hash_lookup
    int32_t x286 = x13[x285];
    int32_t x287 = x285 * 256;
    int32_t x288 = x287 + x286;
    for(int x290=x287; x290 < x288; x290++) {
      int32_t x291 = x9[x290];
      char* x292 = x2[x291];
      int32_t x293 = x3[x291];
      int32_t x294 = x4[x291];
      char* x295 = x5[x291];
      int32_t x296 = x6[x291];
      int32_t x297 = printll(x292);
      printf(",");
      printf("%d",x294);
      printf(",");
      int32_t x301 = printll(x295);
      printf(",");
      int32_t x303 = printll(x217);
      printf("%s\n","");
    }
  }
}
/*****************************************
End of C Generated Code
*******************************************/
