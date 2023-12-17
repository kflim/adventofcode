package structs

type RangeMap struct {
	ranges []valueRange
}

type valueRange struct {
	destStart int
	srcStart  int
	length    int
}

func (r *RangeMap) AddRange(destStart int, srcStart int, length int) {
	r.ranges = append(r.ranges, valueRange{destStart, srcStart, length})
}

func (r *RangeMap) GetDest(val int) int {
	for _, v := range r.ranges {
		if val >= v.srcStart && val < v.srcStart+v.length {
			return v.destStart + (val - v.srcStart)
		}
	}
	return val
}

func (r *RangeMap) GetRanges() []valueRange {
	return r.ranges
}

func (r *valueRange) GetLength() int {
	return r.length
}

func (r *valueRange) GetSrcStart() int {
	return r.srcStart
}
