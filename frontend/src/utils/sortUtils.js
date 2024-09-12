export const sortItems = (a, b, field, sortOrder) => {
    if (typeof a[field] === 'string') {
        return sortOrder === 'asc'
            ? a[field].localeCompare(b[field])
            : b[field].localeCompare(a[field]);
    } else if (typeof a[field] === 'number') {
        return sortOrder === 'asc'
            ? a[field] - b[field]
            : b[field] - a[field];
    }
    return 0;
};
