export function getMaxPage(link: string){

    const links = link.split(',');
    let lastPageUrl = '';
    for (const link of links) {
        if (link.includes('rel="last"')) {
            lastPageUrl = link.split(';')[0].trim();
            break;
        }
    }

    let lastPageNumber = 0;
    if (lastPageUrl) {
        lastPageUrl = lastPageUrl.substring(1, lastPageUrl.length - 1);
        const url = new URL(lastPageUrl);
        lastPageNumber = parseInt(url.searchParams.get('page') || '0');
    }

    return lastPageNumber;

}