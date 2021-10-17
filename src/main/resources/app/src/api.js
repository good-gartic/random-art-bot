const api = "http://localhost:8080"

export default {
    async importJson(file) {
        const data = new FormData();

        data.append("file", file);

        const response = await fetch(api + "/api/links/import", {
            method: "post",
            body: data,
            credentials: "include",
        })
            .then(response => response.json())
            .then(response => response)

        return {
            error: !response.success,
            message: response.message
        }
    },
    async fetchLinksPage(page) {
        return await fetch(api + `/api/links?page=${page}`, {
            method: "get",
            credentials: "include"
        })
        .then(response => response.json())
        .then(response => response);
    },
    async approve(id) {
        const response = await fetch(api + `/api/links/approve/${id}`, {
            method: "post",
            credentials: "include"
        });

        return response.ok;
    },
    async delete(id) {
        const response = await fetch(api + `/api/links/delete/${id}`, {
            method: "post",
            credentials: "include"
        });

        return response.ok;
    },
    async approveAll() {
        const response = await fetch(api + "/api/links/approve-all", {
            method: "post",
            credentials: "include"
        });

        return response.ok;
    }
}