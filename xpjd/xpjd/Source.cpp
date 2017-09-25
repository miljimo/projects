#include "allocator.hpp"
#include "ArrayList.hpp"
#include<vector>


struct  Pool
{
	void    *Ptr;
	size_t   offset;
};
struct FreeBlock
{
	FreeBlock * ptrNext;
	bool        nFlags;
	Pool *      ptrPool;
	void *      ptrEnd;

};


ptrdiff_t  ptr_diff(void * __restrict  diff1, void * __restrict diff2)
{
	return (ptrdiff_t)(((char*)diff2) - ((char*)diff1));
}
int main(int argcs, char * args[])
{

	return  0;
}