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
    }
}